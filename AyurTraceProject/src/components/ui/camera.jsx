import { useState, useRef, useEffect } from "react";
import { Html5QrcodeScanner } from "html5-qrcode";

const Camera = ({ setQrCode }) => {
    const [cameraOpen, setCameraOpen] = useState(false);
    const [availableCameras, setAvailableCameras] = useState([]);
    const [currentCameraId, setCurrentCameraId] = useState(null);
    const [camerasInitialized, setCamerasInitialized] = useState(false);
    const scannerRef = useRef(null);

    const initializeCameras = async () => {
        try {
            // Get all available cameras without requesting permission yet
            const devices = await navigator.mediaDevices.enumerateDevices();
            const videoDevices = devices.filter(device => device.kind === 'videoinput');
            
            if (videoDevices.length > 0) {
                setAvailableCameras(videoDevices);
                
                // Find rear camera (prioritize rear camera)
                const rearCamera = videoDevices.find(camera => 
                    camera.label.toLowerCase().includes('back') ||
                    camera.label.toLowerCase().includes('rear') ||
                    camera.label.toLowerCase().includes('environment') ||
                    camera.label.includes('0') || // Sometimes rear camera is labeled as '0'
                    !camera.label.toLowerCase().includes('front') && !camera.label.toLowerCase().includes('user')
                );
                
                // Set default camera (prefer rear)
                const defaultCameraId = rearCamera ? rearCamera.deviceId : videoDevices[0]?.deviceId;
                setCurrentCameraId(defaultCameraId);
            }
            
            setCamerasInitialized(true);
            
        } catch (error) {
            console.error('Error getting camera list:', error);
            setCamerasInitialized(true);
        }
    };

    // Only initialize cameras list when component mounts, don't request permission
    useEffect(() => {
        initializeCameras();
    }, []);

    const startScanner = async () => {
        if (scannerRef.current) {
            try {
                await scannerRef.current.clear();
            } catch (error) {
                console.error("Error clearing previous scanner:", error);
            }
        }

        try {
            // Request permission and start scanner
            const config = { 
                fps: 10, 
                qrbox: { width: 250, height: 250 },
                videoConstraints: currentCameraId ? {
                    deviceId: { exact: currentCameraId }
                } : {
                    facingMode: { ideal: "environment" }
                }
            };
            
            scannerRef.current = new Html5QrcodeScanner("reader", config, false);

            scannerRef.current.render(
                (decodedText, decodedResult) => {
                    console.log('QR Code detected:', decodedText);
                    setQrCode(decodedText);
                    stopCamera();
                },
                (error) => {
                    // Do nothing if QR not detected
                }
            );
        } catch (error) {
            console.error("Error starting scanner:", error);
        }
    };

    useEffect(() => {
        // Only start scanner when camera is opened and cameras are initialized
        if (cameraOpen && camerasInitialized) {
            // Small delay to ensure DOM is ready
            const timer = setTimeout(() => {
                startScanner();
            }, 100);
            
            return () => clearTimeout(timer);
        }

        return () => {
            if (scannerRef.current) {
                scannerRef.current.clear().catch((error) => {
                    console.error("Failed to clear scanner:", error);
                });
            }
        };
    }, [cameraOpen, currentCameraId, camerasInitialized]);

    const openCamera = async () => {
        if (!camerasInitialized) {
            await initializeCameras();
        }
        setCameraOpen(true);
    };

    const stopCamera = () => {
        if (scannerRef.current) {
            scannerRef.current.clear().then(() => {
                setCameraOpen(false);
            }).catch((error) => {
                console.error("Failed to clear scanner:", error);
                setCameraOpen(false);
            });
        } else {
            setCameraOpen(false);
        }
    };

    const switchCamera = async () => {
        if (availableCameras.length > 1 && scannerRef.current) {
            try {
                // Stop current scanner
                await scannerRef.current.clear();

                // Find next camera
                const currentIndex = availableCameras.findIndex(
                    camera => camera.deviceId === currentCameraId
                );
                const nextIndex = (currentIndex + 1) % availableCameras.length;
                const nextCamera = availableCameras[nextIndex];
                
                // Update camera state
                setCurrentCameraId(nextCamera.deviceId);
                
                console.log('Switching to camera:', nextCamera.label || 'Unknown Camera');
                
            } catch (error) {
                console.error("Failed to switch camera:", error);
            }
        }
    };

    const getCameraLabel = () => {
        if (!currentCameraId || availableCameras.length === 0) return "Default Camera";
        const currentCamera = availableCameras.find(c => c.deviceId === currentCameraId);
        if (!currentCamera) return "Default Camera";
        
        // Simplify camera label
        const label = currentCamera.label;
        if (label.toLowerCase().includes('back') || label.toLowerCase().includes('rear')) {
            return "Back Camera";
        } else if (label.toLowerCase().includes('front')) {
            return "Front Camera";
        } else if (label) {
            return label.split('(')[0].trim() || "Camera";
        } else {
            return "Camera";
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
                <div className="fixed inset-0 bg-black/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
                    <div className="bg-gray-900 rounded-xl shadow-2xl p-6 w-full max-w-md space-y-4 animate-fade-in border border-gray-700">
                        {/* Header with camera switch */}
                        <div className="flex items-center justify-between">
                            <h3 className="text-lg font-semibold text-white">Scan QR Code</h3>
                            {availableCameras.length > 1 && (
                                <button
                                    onClick={switchCamera}
                                    className="flex items-center gap-2 px-3 py-1.5 bg-blue-600 hover:bg-blue-700 text-white text-sm rounded-md transition-all duration-200 hover:scale-105 font-medium"
                                >
                                    <svg 
                                        className="w-4 h-4" 
                                        fill="none" 
                                        stroke="currentColor" 
                                        viewBox="0 0 24 24"
                                    >
                                        <path 
                                            strokeLinecap="round" 
                                            strokeLinejoin="round" 
                                            strokeWidth={2} 
                                            d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" 
                                        />
                                    </svg>
                                    Switch
                                </button>
                            )}
                        </div>
                        
                        {/* Camera reader */}
                        <div 
                            id="reader" 
                            className="w-full aspect-square rounded-lg overflow-hidden border-2 border-gray-600 bg-black"
                        >
                        </div>
                        
                        {/* Camera info - with better contrast */}
                        <div className="text-sm text-gray-300 text-center bg-gray-800 rounded-md p-2">
                            <span className="font-medium">Using:</span> {getCameraLabel()}
                            {availableCameras.length > 1 && (
                                <span className="ml-2 text-blue-400">
                                    ({availableCameras.findIndex(c => c.deviceId === currentCameraId) + 1}/{availableCameras.length})
                                </span>
                            )}
                        </div>
                        
                        {/* Action buttons */}
                        <div className="space-y-2">
                            {/* Switch camera button (full width for better mobile UX) */}
                            {availableCameras.length > 1 && (
                                <button
                                    onClick={switchCamera}
                                    className="w-full py-2.5 bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg transition-all duration-200 hover:shadow-md flex items-center justify-center gap-2"
                                >
                                    <svg 
                                        className="w-5 h-5" 
                                        fill="none" 
                                        stroke="currentColor" 
                                        viewBox="0 0 24 24"
                                    >
                                        <path 
                                            strokeLinecap="round" 
                                            strokeLinejoin="round" 
                                            strokeWidth={2} 
                                            d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" 
                                        />
                                    </svg>
                                    Switch Camera ({availableCameras.length} available)
                                </button>
                            )}
                            
                            <button
                                onClick={stopCamera}
                                className="w-full py-2.5 bg-gray-800 hover:bg-gray-700 text-white font-medium rounded-lg transition-all duration-200 hover:shadow-md border border-gray-600"
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Camera;