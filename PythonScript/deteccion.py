import warnings
warnings.filterwarnings("ignore", category=UserWarning, message=".*autocast.*", module="torch.cuda.amp")

import cv2
import easyocr
from ultralytics import YOLO

# Load YOLOv8 model
model = YOLO("yolov8n.pt")  # or yolov8s.pt, yolov8m.pt, etc.

# Initialize EasyOCR reader
reader = easyocr.Reader(['en'])

# Define vehicle classes (YOLOv8 uses numeric class IDs, so we'll match by name)
vehicle_classes = ['car', 'truck', 'bus', 'motorcycle']  # YOLOv8 uses 'motorcycle' instead of 'motorbike'

# List of authorized plates
authorized_plates = ['SL593LM']

# Start webcam
cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    frame = cv2.flip(frame, 1)
    if not ret:
        break

    # Run YOLOv8 inference
    results = model(frame, verbose=False)[0]

    for box in results.boxes:
        cls_id = int(box.cls[0])
        class_name = model.names[cls_id]
        conf = float(box.conf[0])

        if class_name in vehicle_classes and conf > 0.5:
            x1, y1, x2, y2 = map(int, box.xyxy[0])
            cropped_vehicle = frame[y1:y2, x1:x2]

            # Assume plate is in bottom half of vehicle
            plate_region = cropped_vehicle[cropped_vehicle.shape[0]//2:, :]

            # OCR the cropped plate region
            ocr_results = reader.readtext(plate_region)
            for (bbox, text, prob) in ocr_results:
                cleaned_text = text.upper().replace(" ", "")
                if len(cleaned_text) >= 5:
                    print(f"Detected Plate: {cleaned_text} (conf: {prob:.2f})")
                    if cleaned_text in authorized_plates:
                        print("✅ ACCESS GRANTED")
                        # Trigger relay here if needed
                    else:
                        print("❌ ACCESS DENIED")

            # Draw bounding box and label
            cv2.rectangle(frame, (x1, y1), (x2, y2), (0,255,0), 2)
            cv2.putText(frame, class_name, (x1, y1-10), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (0,255,0), 2)

    # Display the frame
    cv2.imshow("Vehicle Plate Recognition", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
