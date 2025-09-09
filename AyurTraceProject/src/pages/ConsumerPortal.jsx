import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { QrCode, MapPin, Award, FileText, Leaf, Shield, Clock, User } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import Navigation from "@/components/Navigation";
import Footer from "@/components/Footer";
import Camera from "@/components/ui/camera";

const ConsumerPortal = () => {
  const [qrCode, setQrCode] = useState("");
  const [traceData, setTraceData] = useState(null);
  const [isScanning, setIsScanning] = useState(false);
  const [showResults, setShowResults] = useState(false);


  

  // Sample trace data
  const sampleTraceData = {
    productId: "AYR-ASH-2024-001",
    productName: "Premium Ashwagandha Powder",
    batchNumber: "PR001",
    
    collection: {
      collector: "Ravi Kumar",
      location: "Kerala Hills, India",
      date: "2024-01-15",
      coordinates: { lat: 10.8505, lng: 76.2711 },
      method: "Hand-picked, Traditional",
      weather: "Dry, 28°C"
    },
    
    testing: {
      labName: "Ayur Labs Pvt Ltd",
      testDate: "2024-01-16",
      purity: "98.5%",
      contaminants: "None detected",
      activeCompounds: "Withanolides: 2.8%",
      certificate: "AYL-2024-001"
    },
    
    processing: {
      facility: "Green Valley Processing",
      method: "Low-temperature drying",
      processDate: "2024-01-18",
      quality: "Grade A",
      packaging: "Eco-friendly sealed pouches"
    },
    
    certifications: [
      { name: "Organic Certified", issuer: "NPOP India", valid: "2025-01-15" },
      { name: "GMP Certified", issuer: "WHO-GMP", valid: "2024-12-31" },
      { name: "AYUSH Approved", issuer: "Ministry of AYUSH", valid: "2025-06-30" }
    ]
  };

  const handleScan = async () => {
    if (!qrCode.trim()) return;
    
    setIsScanning(true);
    
    // Simulate QR code scanning and data retrieval
    await new Promise(resolve => setTimeout(resolve, 1500));
    
    setTraceData(sampleTraceData);
    setIsScanning(false);
    setShowResults(true);
  };

  const resetScan = () => {
    setQrCode("");
    setTraceData(null);
    setShowResults(false);
  };

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.2,
        delayChildren: 0.3,
      },
    },
  };

  const itemVariants = {
    hidden: { y: 30, opacity: 0 },
    visible: {
      y: 0,
      opacity: 1,
      transition: { duration: 0.6, ease: "easeOut" },
    },
  };

  const cardVariants = {
    hidden: { scale: 0.8, opacity: 0 },
    visible: { 
      scale: 1, 
      opacity: 1,
      transition: { duration: 0.5, ease: "easeOut" }
    },
  };

  return (
    <div className="min-h-screen">
      <Navigation />
      
      <motion.div
        initial="hidden"
        animate="visible"
        variants={containerVariants}
        className="container mx-auto px-6 py-8"
      >
        {/* Header */}
        <motion.div variants={itemVariants} className="text-center mb-8">
          <h1 className="text-4xl font-bold text-foreground mb-2">
            Consumer Portal
          </h1>
          <p className="text-xl ">
            Trace the journey of your Ayurvedic herbs from source to shelf
          </p>
        </motion.div>

        {!showResults ? (
          /* QR Code Scanner Section */
          <motion.div variants={itemVariants} className="max-w-2xl mx-auto">
            <Card className="nature-card">
              <CardHeader className="text-center">
                <CardTitle className="flex items-center justify-center text-card-foreground">
                  <QrCode className="h-6 w-6 mr-2 text-primary" />
                  Scan Product QR Code
                </CardTitle>
                <CardDescription className="text-card-foreground/70">
                  Enter your product's QR code to view its complete traceability information
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-6">
                <motion.div
                  whileFocus={{ scale: 1.02 }}
                  transition={{ duration: 0.2 }}
                >

                  {/* Camera */}
                  <Camera setQrCode={setQrCode} />

                  <Input
                    placeholder="Enter QR code or scan with camera"
                    value={qrCode}
                    onChange={(e) => setQrCode(e.target.value)}
                    className="placeholder-black text-lg py-6 bg-background/50 border-border/30 focus:border-primary focus:ring-primary/20 text-center"
                  />
                </motion.div>
                
                <motion.div
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
                >
                  <Button
                    onClick={handleScan}
                    disabled={isScanning || !qrCode.trim()}
                    className="w-full btn-nature text-lg py-6"
                  >
                    {isScanning ? (
                      <>
                        <motion.div
                          animate={{ rotate: 360 }}
                          transition={{ duration: 1, repeat: Infinity, ease: "linear" }}
                          className="h-5 w-5 border-2 border-current border-t-transparent rounded-full mr-2"
                        />
                        Scanning...
                      </>
                    ) : (
                      <>
                        <QrCode className="h-5 w-5 mr-2" />
                        Trace Product
                      </>
                    )}
                  </Button>
                </motion.div>

                {/* Demo Button */}
                <motion.div
                  whileHover={{ scale: 1.02 }}
                  whileTap={{ scale: 0.98 }}
                >
                  <Button
                    onClick={() => {
                      setQrCode("AYR-ASH-2024-001");
                      handleScan();
                    }}
                    variant="outline"
                    className="w-full border-primary text-primary hover:bg-primary hover:text-primary-foreground"
                  >
                    Try Demo Product
                  </Button>
                </motion.div>
              </CardContent>
            </Card>
          </motion.div>
        ) : (
          /* Traceability Results */
          <AnimatePresence>
            <motion.div
              initial="hidden"
              animate="visible"
              variants={containerVariants}
              className="space-y-8"
            >
              {/* Product Header */}
              <motion.div variants={cardVariants}>
                <Card className="nature-card border-primary/30">
                  <CardContent className="p-6">
                    <div className="flex items-center justify-between mb-4">
                      <div>
                        <h2 className="text-2xl font-bold text-card-foreground">
                          {traceData.productName}
                        </h2>
                        <p className="text-card-foreground/70">
                          Product ID: {traceData.productId} • Batch: {traceData.batchNumber}
                        </p>
                      </div>
                      <motion.div
                        animate={{ scale: [1, 1.1, 1] }}
                        transition={{ duration: 2, repeat: Infinity, repeatDelay: 3 }}
                      >
                        <Shield className="h-10 w-10 text-success" />
                      </motion.div>
                    </div>
                    <Badge className="bg-success text-success-foreground">
                      ✓ Authenticity Verified
                    </Badge>
                  </CardContent>
                </Card>
              </motion.div>

              {/* Journey Timeline */}
              <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                {/* Collection */}
                <motion.div variants={cardVariants} whileHover={{ scale: 1.02 }}>
                  <Card className="nature-card h-full">
                    <CardHeader>
                      <CardTitle className="flex items-center text-card-foreground">
                        <Leaf className="h-5 w-5 mr-2 text-primary" />
                        Collection
                      </CardTitle>
                    </CardHeader>
                    <CardContent className="space-y-3">
                      <div className="flex items-center text-sm text-card-foreground/70">
                        <User className="h-4 w-4 mr-2 text-primary" />
                        {traceData.collection.collector}
                      </div>
                      <div className="flex items-center text-sm text-card-foreground/70">
                        <MapPin className="h-4 w-4 mr-2 text-primary" />
                        {traceData.collection.location}
                      </div>
                      <div className="flex items-center text-sm text-card-foreground/70">
                        <Clock className="h-4 w-4 mr-2 text-primary" />
                        {traceData.collection.date}
                      </div>
                      <div className="pt-2 border-t border-border/20">
                        <p className="text-xs text-card-foreground/60">
                          <strong>Method:</strong> {traceData.collection.method}
                        </p>
                        <p className="text-xs text-card-foreground/60">
                          <strong>Conditions:</strong> {traceData.collection.weather}
                        </p>
                      </div>
                    </CardContent>
                  </Card>
                </motion.div>

                {/* Lab Testing */}
                <motion.div variants={cardVariants} whileHover={{ scale: 1.02 }}>
                  <Card className="nature-card h-full">
                    <CardHeader>
                      <CardTitle className="flex items-center text-card-foreground">
                        <Award className="h-5 w-5 mr-2 text-primary" />
                        Lab Testing
                      </CardTitle>
                    </CardHeader>
                    <CardContent className="space-y-3">
                      <div className="text-sm text-card-foreground/70">
                        <strong>Lab:</strong> {traceData.testing.labName}
                      </div>
                      <div className="text-sm text-card-foreground/70">
                        <strong>Test Date:</strong> {traceData.testing.testDate}
                      </div>
                      <div className="pt-2 border-t border-border/20">
                        <div className="grid grid-cols-2 gap-2 text-xs">
                          <div>
                            <strong className="text-success">Purity:</strong><br />
                            {traceData.testing.purity}
                          </div>
                          <div>
                            <strong className="text-success">Contaminants:</strong><br />
                            {traceData.testing.contaminants}
                          </div>
                        </div>
                        <p className="text-xs text-card-foreground/60 mt-2">
                          <strong>Active Compounds:</strong> {traceData.testing.activeCompounds}
                        </p>
                      </div>
                    </CardContent>
                  </Card>
                </motion.div>

                {/* Processing */}
                <motion.div variants={cardVariants} whileHover={{ scale: 1.02 }}>
                  <Card className="nature-card h-full">
                    <CardHeader>
                      <CardTitle className="flex items-center text-card-foreground">
                        <FileText className="h-5 w-5 mr-2 text-primary" />
                        Processing
                      </CardTitle>
                    </CardHeader>
                    <CardContent className="space-y-3">
                      <div className="text-sm text-card-foreground/70">
                        <strong>Facility:</strong> {traceData.processing.facility}
                      </div>
                      <div className="text-sm text-card-foreground/70">
                        <strong>Process Date:</strong> {traceData.processing.processDate}
                      </div>
                      <div className="pt-2 border-t border-border/20">
                        <p className="text-xs text-card-foreground/60">
                          <strong>Method:</strong> {traceData.processing.method}
                        </p>
                        <p className="text-xs text-card-foreground/60">
                          <strong>Quality:</strong> {traceData.processing.quality}
                        </p>
                        <p className="text-xs text-card-foreground/60">
                          <strong>Packaging:</strong> {traceData.processing.packaging}
                        </p>
                      </div>
                    </CardContent>
                  </Card>
                </motion.div>
              </div>

              {/* Certifications */}
              <motion.div variants={cardVariants}>
                <Card className="nature-card">
                  <CardHeader>
                    <CardTitle className="text-card-foreground">
                      Certifications & Compliance
                    </CardTitle>
                  </CardHeader>
                  <CardContent>
                    <div className="grid md:grid-cols-3 gap-4">
                      {traceData.certifications.map((cert, index) => (
                        <motion.div
                          key={cert.name}
                          initial={{ scale: 0 }}
                          animate={{ scale: 1 }}
                          transition={{ 
                            duration: 0.5, 
                            delay: index * 0.1,
                            type: "spring",
                            stiffness: 200
                          }}
                          whileHover={{ scale: 1.05 }}
                          className="p-4 bg-success/10 border border-success/30 rounded-lg text-center"
                        >
                          <Award className="h-8 w-8 text-success mx-auto mb-2" />
                          <h4 className="font-semibold text-card-foreground">{cert.name}</h4>
                          <p className="text-xs text-card-foreground/70">{cert.issuer}</p>
                          <p className="text-xs text-success">Valid until: {cert.valid}</p>
                        </motion.div>
                      ))}
                    </div>
                  </CardContent>
                </Card>
              </motion.div>

              {/* Action Buttons */}
              <motion.div variants={itemVariants} className="flex justify-center space-x-4">
                <Button
                  onClick={resetScan}
                  className="btn-nature"
                >
                  Scan Another Product
                </Button>
                <Button
                  variant="outline"
                  className="border-primary text-primary hover:bg-primary hover:text-primary-foreground"
                >
                  Download Certificate
                </Button>
              </motion.div>
            </motion.div>
          </AnimatePresence>
        )}
      </motion.div>

      <Footer />
    </div>
  );
};

export default ConsumerPortal;