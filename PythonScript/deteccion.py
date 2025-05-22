import time
import warnings
warnings.filterwarnings("ignore", category=UserWarning, message=".*autocast.*", module="torch.cuda.amp")

import cv2
import easyocr
import mysql.connector
from ultralytics import YOLO

# Cargar el Modelo Yolov8
model = YOLO("yolov8n.pt")

# Inicializar el lector Easyocr
lector = easyocr.Reader(['en'])

# Definir los tipos de vehículos del modelo de yolo
tiposVehiculo = ['car']

# Inicializar webcams para entrada y salida
camara_entrada = cv2.VideoCapture(0)
camara_salida = cv2.VideoCapture(1)

# Contador vehículos
contadorVehiculos = 0
maxVehiculos = 10

# Conexión BBDD
db = mysql.connector.connect(
    host="localhost",
    port=3306,
    user="root",
    passwd="1234",
    database="Sistema_Mat"
)

cursor = db.cursor()

def check_registro(texto_placa):
    query = """select * from matricula as mat 
               join usuario as usu on mat.DNI_Usuario_Resp = usu.DNI 
               where usu.Fecha_Comienzo <= CURRENT_DATE and usu.Fecha_Final >= CURRENT_DATE 
               and mat.Numero_Mat = %s"""
    cursor.execute(query, (texto_placa,))
    return cursor.fetchone()

def detectar_y_procesar_entrada(frame):
    global contadorVehiculos
    results = model(frame, verbose=False)[0]

    for caja in results.boxes:
        clsId = int(caja.cls[0])
        nombreClase = model.names[clsId]
        conf = float(caja.conf[0])

        if nombreClase in tiposVehiculo and conf > 0.5:
            x1, y1, x2, y2 = map(int, caja.xyxy[0])
            vehiculo = frame[y1:y2, x1:x2]

            # Asumir que la placa está en la parte de abajo del vehículo
            plate_region = vehiculo[vehiculo.shape[0]//2:, :]

            # Obtener placa
            ocr_results = lector.readtext(plate_region)
            for (_, text, prob) in ocr_results:
                textoClaro = text.upper().replace(" ", "").replace("-", "").replace(".", "")
                if len(textoClaro) >= 5:
                    print(f"Detected Plate: {textoClaro} (conf: {prob:.2f})")
                    resultadoQuery = check_registro(textoClaro)
                    if resultadoQuery is not None:
                        if contadorVehiculos < maxVehiculos:
                            print(f"✅ Acceso permitido. Bienvenido {resultadoQuery[5]}.")
                            contadorVehiculos += 1
                            time.sleep(5)  # Esperar para evitar múltiples cuentas seguidas
                        else:
                            print("❌ Acceso denegado: No hay plazas libres")
                    else:
                        print("❌ Acceso denegado: Vehículo no autorizado")

            cv2.rectangle(frame, (x1, y1), (x2, y2), (0, 255, 0), 2)
            cv2.putText(frame, nombreClase, (x1, y1 - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (0, 255, 0), 2)

    cv2.putText(frame, f"Contador: {contadorVehiculos}", (20, 50),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 1)
    return frame


def detectar_y_procesar_salida(frame):
    global contadorVehiculos
    results = model(frame, verbose=False)[0]

    for caja in results.boxes:
        clsId = int(caja.cls[0])
        nombreClase = model.names[clsId]
        conf = float(caja.conf[0])

        if nombreClase in tiposVehiculo and conf > 0.5:
            x1, y1, x2, y2 = map(int, caja.xyxy[0])

            if contadorVehiculos > 0:
                contadorVehiculos -= 1
            print(f"⛔ Vehículo Saliendo. ¡Hasta Pronto!")
            time.sleep(5)
            cv2.rectangle(frame, (x1, y1), (x2, y2), (255, 0, 0), 2)
            cv2.putText(frame, "Salida", (x1, y1 - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (255, 0, 0), 2)
            break  # Solo contar un vehículo por frame

    cv2.putText(frame, f"Contador: {contadorVehiculos}", (20, 50),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 1)
    return frame


while True:
    ret_entrada, frame_entrada = camara_entrada.read()
    ret_salida, frame_salida = camara_salida.read()

    if ret_entrada:
        frame_entrada = detectar_y_procesar_entrada(frame_entrada)
        cv2.imshow("Camara Entrada", frame_entrada)

    if ret_salida:
        frame_salida = detectar_y_procesar_salida(frame_salida)
        cv2.imshow("Camara Salida", frame_salida)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

camara_entrada.release()
camara_salida.release()
cv2.destroyAllWindows()
