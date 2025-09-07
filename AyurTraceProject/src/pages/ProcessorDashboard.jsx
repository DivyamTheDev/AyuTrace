import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Factory, Cog, Package, TrendingUp, CheckCircle, Clock, AlertTriangle } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Badge } from "@/components/ui/badge";
import Navigation from "@/components/Navigation";
import Footer from "@/components/Footer";

const ProcessorDashboard = () => {
  const [batches, setBatches] = useState([
    {
      id: "PR001",
      herbName: "Ashwagandha Powder",
      rawMaterial: "LB002",
      stage: "Processing",
      progress: 75,
      startDate: "2024-01-16",
      estimatedCompletion: "2024-01-20",
    },
    {
      id: "PR002", 
      herbName: "Turmeric Extract",
      rawMaterial: "LB001",
      stage: "Quality Check",
      progress: 90,
      startDate: "2024-01-14",
      estimatedCompletion: "2024-01-18",
    },
    {
      id: "PR003",
      herbName: "Brahmi Tablets",
      rawMaterial: "LB003",
      stage: "Packaging",
      progress: 95,
      startDate: "2024-01-12",
      estimatedCompletion: "2024-01-17",
    },
  ]);

  const [selectedBatch, setSelectedBatch] = useState(null);

  const processingSteps = [
    { id: 1, name: "Raw Material Received", completed: true, icon: Package },
    { id: 2, name: "Quality Inspection", completed: true, icon: CheckCircle },
    { id: 3, name: "Primary Processing", completed: true, icon: Cog },
    { id: 4, name: "Secondary Processing", completed: false, icon: Factory },
    { id: 5, name: "Quality Testing", completed: false, icon: AlertTriangle },
    { id: 6, name: "Packaging", completed: false, icon: Package },
    { id: 7, name: "Final Inspection", completed: false, icon: CheckCircle },
  ];

  const getStageColor = (stage) => {
    switch (stage) {
      case "Processing": return "bg-primary text-primary-foreground";
      case "Quality Check": return "bg-amber-500 text-white";
      case "Packaging": return "bg-success text-success-foreground";
      case "Completed": return "bg-success text-success-foreground";
      default: return "bg-muted text-muted-foreground";
    }
  };

  const updateBatchProgress = (batchId) => {
    setBatches(prev => 
      prev.map(batch => 
        batch.id === batchId 
          ? { ...batch, progress: Math.min(100, batch.progress + 10) }
          : batch
      )
    );
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
            Processor Dashboard
          </h1>
          <p className="text-xl">
            Monitor and manage herb processing operations
          </p>
        </motion.div>

        {/* Statistics */}
        <motion.div variants={itemVariants} className="grid md:grid-cols-4 gap-6 mb-8">
          {[
            { label: "Active Batches", value: "23", icon: Factory, color: "text-primary" },
            { label: "Completed Today", value: "8", icon: CheckCircle, color: "text-success" },
            { label: "Processing Efficiency", value: "94%", icon: TrendingUp, color: "text-success" },
            { label: "Avg. Processing Time", value: "4.2d", icon: Clock, color: "text-amber-500" },
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
                    <motion.div
                      animate={{ rotate: stat.icon === Factory ? 360 : 0 }}
                      transition={{ 
                        duration: stat.icon === Factory ? 4 : 0,
                        repeat: stat.icon === Factory ? Infinity : 0,
                        ease: "linear"
                      }}
                    >
                      <stat.icon className={`h-8 w-8 ${stat.color}`} />
                    </motion.div>
                  </div>
                </CardContent>
              </Card>
            </motion.div>
          ))}
        </motion.div>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Processing Timeline */}
          <motion.div variants={itemVariants} className="lg:col-span-1">
            <Card className="nature-card sticky top-24">
              <CardHeader>
                <CardTitle className="flex items-center text-card-foreground">
                  <Cog className="h-5 w-5 mr-2 text-primary" />
                  Processing Steps
                </CardTitle>
                <CardDescription className="text-card-foreground/70">
                  Track progress through each stage
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {processingSteps.map((step, index) => {
                    const Icon = step.icon;
                    return (
                      <motion.div
                        key={step.id}
                        initial={{ x: -50, opacity: 0 }}
                        animate={{ x: 0, opacity: 1 }}
                        transition={{ 
                          duration: 0.5, 
                          delay: index * 0.1,
                          ease: "easeOut"
                        }}
                        whileHover={{ scale: 1.02, x: 5 }}
                        className={`flex items-center space-x-3 p-3 rounded-lg border ${
                          step.completed 
                            ? "bg-success/10 border-success/30" 
                            : "bg-card-foreground/5 border-border/30"
                        }`}
                      >
                        <motion.div
                          animate={{ 
                            scale: step.completed ? [1, 1.2, 1] : 1,
                            rotate: step.icon === Cog && !step.completed ? 360 : 0
                          }}
                          transition={{ 
                            scale: { duration: 0.5, delay: index * 0.1 },
                            rotate: { 
                              duration: 2, 
                              repeat: step.icon === Cog && !step.completed ? Infinity : 0,
                              ease: "linear"
                            }
                          }}
                          className={`p-2 rounded-full ${
                            step.completed 
                              ? "bg-success text-success-foreground" 
                              : "bg-muted text-muted-foreground"
                          }`}
                        >
                          <Icon className="h-4 w-4" />
                        </motion.div>
                        <span className={`text-sm font-medium ${
                          step.completed ? "text-success" : "text-card-foreground/70"
                        }`}>
                          {step.name}
                        </span>
                      </motion.div>
                    );
                  })}
                </div>
              </CardContent>
            </Card>
          </motion.div>

          {/* Active Batches */}
          <div className="lg:col-span-2">
            <motion.div variants={itemVariants} className="mb-6">
              <h2 className="text-2xl font-bold text-foreground mb-2">
                Active Processing Batches
              </h2>
              <p className="">
                Monitor real-time processing status and progress
              </p>
            </motion.div>

            <div className="space-y-4">
              {batches.map((batch, index) => (
                <motion.div
                  key={batch.id}
                  initial={{ scale: 0.9, opacity: 0 }}
                  animate={{ scale: 1, opacity: 1 }}
                  transition={{ 
                    duration: 0.5,
                    delay: index * 0.1,
                    ease: "easeOut"
                  }}
                  whileHover={{ scale: 1.02, y: -2 }}
                >
                  <Card className="nature-card border-border/20">
                    <CardContent className="p-6">
                      <div className="flex items-center justify-between mb-4">
                        <div>
                          <h3 className="text-xl font-semibold text-card-foreground mb-1">
                            {batch.herbName}
                          </h3>
                          <p className="text-sm text-card-foreground/70">
                            Batch ID: {batch.id} • Raw Material: {batch.rawMaterial}
                          </p>
                        </div>
                        <Badge className={getStageColor(batch.stage)}>
                          {batch.stage}
                        </Badge>
                      </div>

                      <div className="space-y-4">
                        <div>
                          <div className="flex justify-between text-sm text-card-foreground/70 mb-2">
                            <span>Progress</span>
                            <span>{batch.progress}%</span>
                          </div>
                          <motion.div
                            initial={{ width: 0 }}
                            animate={{ width: "100%" }}
                            transition={{ duration: 1, delay: index * 0.2 }}
                          >
                            <Progress value={batch.progress} className="h-2" />
                          </motion.div>
                        </div>

                        <div className="grid md:grid-cols-2 gap-4 text-sm text-card-foreground/70">
                          <div>
                            <span className="font-medium">Started:</span> {batch.startDate}
                          </div>
                          <div>
                            <span className="font-medium">Est. Completion:</span> {batch.estimatedCompletion}
                          </div>
                        </div>

                        <motion.div
                          whileHover={{ scale: 1.05 }}
                          whileTap={{ scale: 0.95 }}
                        >
                          <Button
                            onClick={() => updateBatchProgress(batch.id)}
                            className="btn-nature w-full"
                            disabled={batch.progress >= 100}
                          >
                            {batch.progress >= 100 ? (
                              <>
                                <CheckCircle className="h-4 w-4 mr-2" />
                                Completed
                              </>
                            ) : (
                              <>
                                <Cog className="h-4 w-4 mr-2" />
                                Update Progress
                              </>
                            )}
                          </Button>
                        </motion.div>
                      </div>
                    </CardContent>
                  </Card>
                </motion.div>
              ))}
            </div>
          </div>
        </div>
      </motion.div>

      <Footer />
    </div>
  );
};

export default ProcessorDashboard;