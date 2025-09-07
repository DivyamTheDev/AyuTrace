import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { 
  Users, Package, FlaskConical, Factory, TrendingUp, AlertTriangle,
  BarChart3, PieChart, Activity, DollarSign, Leaf, Shield
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Progress } from "@/components/ui/progress";
import Navigation from "@/components/Navigation";
import Footer from "@/components/Footer";

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    totalCollectors: 0,
    harvestVolume: 0,
    qualityRate: 0,
    revenue: 0,
  });

  const [alerts] = useState([
    {
      id: 1,
      type: "warning",
      message: "Quality test failure rate increased by 5% this week",
      time: "2 hours ago",
    },
    {
      id: 2,
      type: "info", 
      message: "New collector registration from Rajasthan region",
      time: "4 hours ago",
    },
    {
      id: 3,
      type: "success",
      message: "Monthly quality target achieved: 96.5%",
      time: "1 day ago",
    },
  ]);

  // Animated counter effect
  useEffect(() => {
    const timer = setTimeout(() => {
      setStats({
        totalCollectors: 247,
        harvestVolume: 1850,
        qualityRate: 94.8,
        revenue: 125000,
      });
    }, 500);
    return () => clearTimeout(timer);
  }, []);

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

  const counterVariants = {
    hidden: { scale: 0.8, opacity: 0 },
    visible: { 
      scale: 1, 
      opacity: 1,
      transition: { duration: 0.8, ease: "easeOut" }
    },
  };

  const getAlertStyle = (type) => {
    switch (type) {
      case "warning": return "border-amber-500/30 bg-amber-500/10 text-amber-600";
      case "success": return "border-success/30 bg-success/10 text-success";
      case "info": return "border-primary/30 bg-primary/10 text-primary";
      default: return "border-border/30 bg-card/50";
    }
  };

  const getAlertIcon = (type) => {
    switch (type) {
      case "warning": return <AlertTriangle className="h-4 w-4" />;
      case "success": return <Shield className="h-4 w-4" />;
      case "info": return <Activity className="h-4 w-4" />;
      default: return null;
    }
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
            Admin Dashboard
          </h1>
          <p className="text-xl ">
            Monitor system performance and network analytics
          </p>
        </motion.div>

        {/* Key Metrics */}
        <motion.div variants={itemVariants} className="grid md:grid-cols-4 gap-6 mb-8">
          {[
            { 
              label: "Active Collectors", 
              value: stats.totalCollectors, 
              icon: Users, 
              color: "text-primary",
              suffix: ""
            },
            { 
              label: "Harvest Volume (kg)", 
              value: stats.harvestVolume, 
              icon: Package, 
              color: "text-success",
              suffix: "kg"
            },
            { 
              label: "Quality Pass Rate", 
              value: stats.qualityRate, 
              icon: Shield, 
              color: "text-success",
              suffix: "%"
            },
            { 
              label: "Monthly Revenue", 
              value: stats.revenue, 
              icon: DollarSign, 
              color: "text-primary",
              prefix: "$",
              suffix: ""
            },
          ].map((metric, index) => (
            <motion.div
              key={metric.label}
              variants={counterVariants}
              whileHover={{ scale: 1.05, y: -5 }}
              transition={{ duration: 0.2 }}
            >
              <Card className="nature-card">
                <CardContent className="p-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm text-card-foreground/70">{metric.label}</p>
                      <motion.p 
                        className={`text-3xl font-bold ${metric.color}`}
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        transition={{ 
                          duration: 1,
                          delay: index * 0.2,
                          ease: "easeOut"
                        }}
                      >
                        {metric.prefix || ""}
                        <motion.span
                          initial={{ textContent: 0 }}
                          animate={{ textContent: metric.value }}
                          transition={{ 
                            duration: 2,
                            delay: index * 0.2,
                            ease: "easeOut"
                          }}
                        >
                          {metric.value}
                        </motion.span>
                        {metric.suffix}
                      </motion.p>
                    </div>
                    <motion.div
                      animate={{ 
                        scale: [1, 1.2, 1],
                        rotate: metric.icon === Activity ? [0, 180, 360] : 0
                      }}
                      transition={{ 
                        duration: 3,
                        delay: index * 0.3,
                        repeat: Infinity,
                        repeatDelay: 5
                      }}
                    >
                      <metric.icon className={`h-8 w-8 ${metric.color}`} />
                    </motion.div>
                  </div>
                </CardContent>
              </Card>
            </motion.div>
          ))}
        </motion.div>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Network Overview */}
          <div className="lg:col-span-2 space-y-6">
            {/* Processing Pipeline */}
            <motion.div variants={itemVariants}>
              <Card className="nature-card">
                <CardHeader>
                  <CardTitle className="flex items-center text-card-foreground">
                    <BarChart3 className="h-5 w-5 mr-2 text-primary" />
                    Processing Pipeline Status
                  </CardTitle>
                  <CardDescription className="text-card-foreground/70">
                    Real-time view of herbs moving through the system
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-6">
                  {[
                    { stage: "Collection", count: 45, total: 50, color: "bg-primary" },
                    { stage: "Lab Testing", count: 28, total: 35, color: "bg-amber-500" },
                    { stage: "Processing", count: 18, total: 25, color: "bg-blue-500" },
                    { stage: "Packaging", count: 22, total: 22, color: "bg-success" },
                  ].map((stage, index) => {
                    const percentage = (stage.count / stage.total) * 100;
                    return (
                      <motion.div 
                        key={stage.stage}
                        initial={{ x: -50, opacity: 0 }}
                        animate={{ x: 0, opacity: 1 }}
                        transition={{ 
                          duration: 0.6,
                          delay: index * 0.1,
                          ease: "easeOut"
                        }}
                        className="space-y-2"
                      >
                        <div className="flex justify-between text-sm text-card-foreground">
                          <span className="font-medium">{stage.stage}</span>
                          <span>{stage.count}/{stage.total} batches</span>
                        </div>
                        <motion.div
                          initial={{ width: 0 }}
                          animate={{ width: "100%" }}
                          transition={{ duration: 1, delay: index * 0.2 }}
                        >
                          <Progress 
                            value={percentage} 
                            className="h-3" 
                            style={{ '--progress-background': stage.color }}
                          />
                        </motion.div>
                      </motion.div>
                    );
                  })}
                </CardContent>
              </Card>
            </motion.div>

            {/* Regional Distribution */}
            <motion.div variants={itemVariants}>
              <Card className="nature-card">
                <CardHeader>
                  <CardTitle className="flex items-center text-card-foreground">
                    <PieChart className="h-5 w-5 mr-2 text-primary" />
                    Regional Collection Distribution
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="grid md:grid-cols-2 gap-6">
                    {[
                      { region: "Kerala", percentage: 35, herbs: ["Ashwagandha", "Cardamom"] },
                      { region: "Tamil Nadu", percentage: 25, herbs: ["Turmeric", "Ginger"] },
                      { region: "Rajasthan", percentage: 20, herbs: ["Brahmi", "Shankhpushpi"] },
                      { region: "Himachal Pradesh", percentage: 20, herbs: ["Jatamansi", "Kutki"] },
                    ].map((region, index) => (
                      <motion.div
                        key={region.region}
                        initial={{ scale: 0.8, opacity: 0 }}
                        animate={{ scale: 1, opacity: 1 }}
                        transition={{ 
                          duration: 0.5,
                          delay: index * 0.1,
                          ease: "easeOut"
                        }}
                        whileHover={{ scale: 1.02 }}
                        className="p-4 bg-card-foreground/5 rounded-lg border border-border/20"
                      >
                        <div className="flex items-center justify-between mb-2">
                          <h4 className="font-semibold text-card-foreground">{region.region}</h4>
                          <Badge variant="secondary">{region.percentage}%</Badge>
                        </div>
                        <div className="flex flex-wrap gap-1">
                          {region.herbs.map((herb, herbIndex) => (
                            <motion.span
                              key={herb}
                              initial={{ opacity: 0, y: 10 }}
                              animate={{ opacity: 1, y: 0 }}
                              transition={{ 
                                duration: 0.3,
                                delay: (index * 0.1) + (herbIndex * 0.05)
                              }}
                              className="inline-flex items-center text-xs bg-primary/20 text-primary px-2 py-1 rounded-full"
                            >
                              <Leaf className="h-3 w-3 mr-1" />
                              {herb}
                            </motion.span>
                          ))}
                        </div>
                      </motion.div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            </motion.div>
          </div>

          {/* Alerts & Notifications */}
          <div className="space-y-6">
            <motion.div variants={itemVariants}>
              <Card className="nature-card">
                <CardHeader>
                  <CardTitle className="flex items-center text-card-foreground">
                    <AlertTriangle className="h-5 w-5 mr-2 text-primary" />
                    System Alerts
                  </CardTitle>
                  <CardDescription className="text-card-foreground/70">
                    Recent notifications and system status
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                  {alerts.map((alert, index) => (
                    <motion.div
                      key={alert.id}
                      initial={{ x: 50, opacity: 0 }}
                      animate={{ x: 0, opacity: 1 }}
                      transition={{ 
                        duration: 0.5,
                        delay: index * 0.1,
                        ease: "easeOut"
                      }}
                      whileHover={{ 
                        scale: 1.02,
                        x: 5,
                        transition: { duration: 0.2 }
                      }}
                      className={`p-4 rounded-lg border ${getAlertStyle(alert.type)}`}
                    >
                      <div className="flex items-start space-x-3">
                        <motion.div
                          animate={{ 
                            scale: alert.type === "warning" ? [1, 1.2, 1] : 1 
                          }}
                          transition={{ 
                            duration: 2,
                            repeat: alert.type === "warning" ? Infinity : 0,
                            repeatDelay: 3
                          }}
                        >
                          {getAlertIcon(alert.type)}
                        </motion.div>
                        <div className="flex-1">
                          <p className="text-sm font-medium">{alert.message}</p>
                          <p className="text-xs opacity-70 mt-1">{alert.time}</p>
                        </div>
                      </div>
                    </motion.div>
                  ))}
                </CardContent>
              </Card>
            </motion.div>

            {/* Quick Actions */}
            <motion.div variants={itemVariants}>
              <Card className="nature-card">
                <CardHeader>
                  <CardTitle className="text-card-foreground">Quick Actions</CardTitle>
                </CardHeader>
                <CardContent className="space-y-3">
                  {[
                    { label: "Export Monthly Report", icon: BarChart3 },
                    { label: "System Backup", icon: Shield },
                    { label: "Send Notifications", icon: Activity },
                    { label: "Network Analysis", icon: TrendingUp },
                  ].map((action, index) => (
                    <motion.div
                      key={action.label}
                      whileHover={{ scale: 1.02 }}
                      whileTap={{ scale: 0.98 }}
                    >
                      <Button
                        variant="outline"
                        className="w-full justify-start border-border/30 hover:bg-primary hover:text-primary-foreground hover:border-primary"
                      >
                        <action.icon className="h-4 w-4 mr-2" />
                        {action.label}
                      </Button>
                    </motion.div>
                  ))}
                </CardContent>
              </Card>
            </motion.div>
          </div>
        </div>
      </motion.div>

      <Footer />
    </div>
  );
};

export default AdminDashboard;