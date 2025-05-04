import warnings
warnings.filterwarnings("ignore", category=UserWarning, message=".*autocast.*", module="torch.cuda.amp")

import cv2
import easyocr
from ultralytics import YOLO

# Cargar el Modelo Yolov8
model = YOLO("yolov8n.pt")

# Inicializar el lector Easyocr
lector = easyocr.Reader(['en'])

# Definir los tipos de vehículos del modelo de yolo
tiposVehiculo = ['car', 'truck', 'bus', 'motorcycle']

# List of authorized plates
matriculasPermitidas = ['GP255PV', 'M666YOB','SL593M']

# Inicializar webcam
cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    #frame = cv2.flip(frame, 1)
    if not ret:
        break

    # Run YOLOv8 inference
    results = model(frame, verbose=False)[0]

    for caja in results.boxes:
        clsId = int(caja.cls[0])
        nombreClase = model.names[clsId]
        conf = float(caja.conf[0])

        if nombreClase in tiposVehiculo and conf > 0.5:
            x1, y1, x2, y2 = map(int, caja.xyxy[0])
            cropped_vehicle = frame[y1:y2, x1:x2]

            # Assume plate is in bottom half of vehicle
            plate_region = cropped_vehicle[cropped_vehicle.shape[0]//2:, :]

            # OCR the cropped plate region
            ocr_results = lector.readtext(plate_region)
            for (bbox, text, prob) in ocr_results:
                textoClaro = text.upper().replace(" ", "").replace("-", "").replace(".","")
                if len(textoClaro) >= 5:
                    print(f"Detected Plate: {textoClaro} (conf: {prob:.2f})")
                    if textoClaro in matriculasPermitidas:
                        print(f"Placa dectada: {textoClaro} ✅ Acceso permitido")
                    #else:
                     #   print("❌ Acceso denegado")

            # Dibujar cajas y texto de objetos
            cv2.rectangle(frame, (x1, y1), (x2, y2), (0,255,0), 2)
            cv2.putText(frame, nombreClase, (x1, y1-10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (0,255,0), 2)

    # Mostrar imagen
    cv2.imshow("Reconocimiento de matrículas", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
