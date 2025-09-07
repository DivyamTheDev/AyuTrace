import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { FlaskConical, Upload, CheckCircle, AlertCircle, FileText, Download } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import Navigation from "@/components/Navigation";
import Footer from "@/components/Footer";

const LabDashboard = () => {
  const [samples, setSamples] = useState([
    {
      id: "LB001",
      herbName: "Ashwagandha",
      collector: "Ravi Kumar",
      dateReceived: "2024-01-15",
      status: "Testing",
      purity: null,
      contaminants: null,
    },
    {
      id: "LB002", 
      herbName: "Turmeric",
      collector: "Priya Sharma", 
      dateReceived: "2024-01-12",
      status: "Completed",
      purity: "98.5%",
      contaminants: "None detected",
    },
    {
      id: "LB003",
      herbName: "Brahmi",
      collector: "Amit Patel",
      dateReceived: "2024-01-10", 
      status: "Failed",
      purity: "76.2%",
      contaminants: "Heavy metals detected",
    },
  ]);

  const [uploadingFile, setUploadingFile] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");

  const handleFileUpload = async (sampleId) => {
    setUploadingFile(sampleId);
    
    // Simulate file upload and processing
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    setSamples(prevSamples => 
      prevSamples.map(sample => 
        sample.id === sampleId 
          ? { 
              ...sample, 
              status: "Completed",
              purity: "96.7%",
              contaminants: "None detected"
            }
          : sample
      )
    );
    
    setUploadingFile(false);
    setSuccessMessage(`Test results uploaded for ${sampleId}`);
    setTimeout(() => setSuccessMessage(""), 3000);
  };

  const getStatusColor = (status) => {
    switch (status) {
      case "Completed": return "bg-success text-success-foreground";
      case "Testing": return "bg-primary text-primary-foreground";
      case "Failed": return "bg-destructive text-destructive-foreground";
      default: return "bg-muted text-muted-foreground";
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case "Completed": return <CheckCircle className="h-4 w-4" />;
      case "Testing": return <FlaskConical className="h-4 w-4" />;
      case "Failed": return <AlertCircle className="h-4 w-4" />;
      default: return null;
    }
  };

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1,
        delayChildren: 0.2,
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
        <motion.div variants={itemVariants} className="mb-8">
          <h1 className="text-4xl font-bold text-foreground mb-2">
            Laboratory Dashboard
          </h1>
          <p className="text-xl ">
            Manage quality testing and certification for herb samples
          </p>
        </motion.div>

        {/* Statistics Cards */}
        <motion.div variants={itemVariants} className="grid md:grid-cols-4 gap-6 mb-8">
          {[
            { label: "Samples Received", value: "127", icon: FlaskConical, color: "text-primary" },
            { label: "Tests Completed", value: "98", icon: CheckCircle, color: "text-success" },
            { label: "Quality Passed", value: "92%", icon: CheckCircle, color: "text-success" },
            { label: "Pending Tests", value: "29", icon: AlertCircle, color: "text-primary" },
          ].map((stat, index) => (
            <motion.div
              key={stat.label}
              whileHover={{ scale: 1.05, y: -5 }}
              transition={{ duration: 0.2 }}
            >
              <Card className="nature-card">
                <CardContent className="p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm text-card-foreground/70">{stat.label}</p>
                      <motion.p 
                        className={`text-2xl font-bold ${stat.color}`}
                        animate={{ scale: [1, 1.1, 1] }}
                        transition={{ 
                          duration: 2,
                          delay: index * 0.2,
                          repeat: Infinity,
                          repeatDelay: 5
                        }}
                      >
                        {stat.value}
                      </motion.p>
                    </div>
                    <stat.icon className={`h-8 w-8 ${stat.color}`} />
                  </div>
                </CardContent>
              </Card>
            </motion.div>
          ))}
        </motion.div>

        {/* Samples Table */}
        <motion.div variants={itemVariants}>
          <Card className="nature-card">
            <CardHeader>
              <CardTitle className="flex items-center text-card-foreground">
                <FlaskConical className="h-5 w-5 mr-2 text-primary" />
                Sample Testing Queue
              </CardTitle>
              <CardDescription className="text-card-foreground/70">
                Manage and track herb sample testing status
              </CardDescription>
            </CardHeader>
            <CardContent>
              <div className="overflow-x-auto">
                <Table>
                  <TableHeader>
                    <TableRow className="border-border/30">
                      <TableHead className="text-card-foreground">Sample ID</TableHead>
                      <TableHead className="text-card-foreground">Herb Name</TableHead>
                      <TableHead className="text-card-foreground">Collector</TableHead>
                      <TableHead className="text-card-foreground">Date Received</TableHead>
                      <TableHead className="text-card-foreground">Status</TableHead>
                      <TableHead className="text-card-foreground">Purity</TableHead>
                      <TableHead className="text-card-foreground">Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    <AnimatePresence>
                      {samples.map((sample, index) => (
                        <motion.tr
                          key={sample.id}
                          initial={{ opacity: 0, x: -50 }}
                          animate={{ opacity: 1, x: 0 }}
                          exit={{ opacity: 0, x: 50 }}
                          transition={{ 
                            duration: 0.5,
                            delay: index * 0.1,
                            ease: "easeOut"
                          }}
                          className="border-border/30 hover:bg-card-foreground/5"
                        >
                          <TableCell className="font-medium text-card-foreground">
                            {sample.id}
                          </TableCell>
                          <TableCell className="text-card-foreground">
                            {sample.herbName}
                          </TableCell>
                          <TableCell className="text-card-foreground">
                            {sample.collector}
                          </TableCell>
                          <TableCell className="text-card-foreground">
                            {sample.dateReceived}
                          </TableCell>
                          <TableCell>
                            <motion.div
                              whileHover={{ scale: 1.05 }}
                              transition={{ duration: 0.2 }}
                            >
                              <Badge className={getStatusColor(sample.status)}>
                                <span className="flex items-center">
                                  {getStatusIcon(sample.status)}
                                  <span className="ml-1">{sample.status}</span>
                                </span>
                              </Badge>
                            </motion.div>
                          </TableCell>
                          <TableCell className="text-card-foreground">
                            {sample.purity || "Pending"}
                          </TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              {sample.status === "Testing" && (
                                <motion.div
                                  whileHover={{ scale: 1.05 }}
                                  whileTap={{ scale: 0.95 }}
                                >
                                  <Button
                                    size="sm"
                                    onClick={() => handleFileUpload(sample.id)}
                                    disabled={uploadingFile === sample.id}
                                    className="btn-nature text-xs"
                                  >
                                    {uploadingFile === sample.id ? (
                                      <motion.div
                                        animate={{ rotate: 360 }}
                                        transition={{ 
                                          duration: 1, 
                                          repeat: Infinity, 
                                          ease: "linear" 
                                        }}
                                        className="h-3 w-3 border border-current border-t-transparent rounded-full"
                                      />
                                    ) : (
                                      <>
                                        <Upload className="h-3 w-3 mr-1" />
                                        Upload Results
                                      </>
                                    )}
                                  </Button>
                                </motion.div>
                              )}
                              
                              {sample.status === "Completed" && (
                                <motion.div
                                  whileHover={{ scale: 1.05 }}
                                  whileTap={{ scale: 0.95 }}
                                >
                                  <Button
                                    size="sm"
                                    variant="outline"
                                    className="text-xs border-primary text-primary hover:bg-primary hover:text-primary-foreground"
                                  >
                                    <Download className="h-3 w-3 mr-1" />
                                    Certificate
                                  </Button>
                                </motion.div>
                              )}
                            </div>
                          </TableCell>
                        </motion.tr>
                      ))}
                    </AnimatePresence>
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </motion.div>

        {/* Success Message */}
        <AnimatePresence>
          {successMessage && (
            <motion.div
              initial={{ scale: 0, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0, opacity: 0 }}
              className="fixed top-4 right-4 z-50"
            >
              <Card className="bg-success text-success-foreground shadow-glow">
                <CardContent className="p-4">
                  <div className="flex items-center">
                    <CheckCircle className="h-5 w-5 mr-2" />
                    {successMessage}
                  </div>
                </CardContent>
              </Card>
            </motion.div>
          )}
        </AnimatePresence>
      </motion.div>

      <Footer />
    </div>
  );
};

export default LabDashboard;