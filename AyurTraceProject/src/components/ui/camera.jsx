import { useState, useRef, useEffect } from "react";
import { Html5QrcodeScanner } from "html5-qrcode";

const Camera = ({ setQrCode }) => {   // receive setQrCode from props
    const [cameraOpen, setCameraOpen] = useState(false);
    const scannerRef = useRef(null);

    useEffect(() => {
        if (cameraOpen) {
            const config = { fps: 10, qrbox: { width: 250, height: 250 } };
            scannerRef.current = new Html5QrcodeScanner("reader", config, false);

            scannerRef.current.render(
                (decodedText, decodedResult) => {
                    setQrCode(decodedText);   // update QR code in parent
                    stopCamera();
                },
                (error) => {
                    // Do nothing if QR not detected
                }
            );
        }
        return () => {
            if (scannerRef.current) {
                scannerRef.current.clear().catch((error) => {
                    console.error("Failed to clear scanner:", error);
                });
            }
        };
    }, [cameraOpen]);

    const openCamera = () => {
        setCameraOpen(true);
    };

    const stopCamera = () => {
        if (scannerRef.current) {
            scannerRef.current.clear().then(() => {
                setCameraOpen(false);
            }).catch((error) => {
                console.error("Failed to clear scanner:", error);
            });
        }
    };

    return (
        <div className="flex flex-col items-center justify-center -mt-8 w-full max-w-2xl mx-auto p-4 text-white">
            <button
                onClick={openCamera}
                className="px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg shadow-md transition-all duration-200 hover:shadow-lg active:scale-95"
            >
                Scan QR Code
            </button>

            {cameraOpen && (
                <div className="fixed inset-0 bg-black/80 backdrop-blur-sm flex items-center justify-center z-50 p-4 text-white">
                    <div className="bg-black rounded-xl shadow-2xl p-6 w-full max-w-md space-y-4 animate-fade-in">
                        <h3 className="text-lg font-semibold text-white">Scan QR Code</h3>
                        <div id="reader" className="w-full aspect-square rounded-lg overflow-hidden border-2 border-gray-200"
                            style={{ backgroundColor: "#000" }}>
                        </div>
                        <button
                            onClick={stopCamera}
                            className="w-full py-2.5 bg-gray-800 hover:bg-gray-700 text-white font-medium rounded-lg transition-all duration-200 hover:shadow-md"
                        >
                            Cancel
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Camera;
