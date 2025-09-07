import { motion } from "framer-motion";
import { Link } from "react-router-dom";
import { Leaf, Shield, Zap, Users, ArrowRight, CheckCircle } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import Navigation from "@/components/Navigation";
import Footer from "@/components/Footer";
import heroImage from "@/assets/hero-herbs.jpg";

const HomePage = () => {
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.3,
        delayChildren: 0.2,
      },
    },
  };

  const itemVariants = {
    hidden: { y: 30, opacity: 0 },
    visible: {
      y: 0,
      opacity: 1,
      transition: { duration: 0.8, ease: "easeOut" },
    },
  };

  const features = [
    {
      icon: Shield,
      title: "Authentic Verification",
      description: "Blockchain-based verification ensures genuine Ayurvedic herbs from farm to pharmacy.",
    },
    {
      icon: Zap,
      title: "Real-time Tracking",
      description: "Track your herbs journey through collection, testing, processing, and distribution.",
    },
    {
      icon: Users,
      title: "Trusted Network",
      description: "Connect collectors, labs, processors, and consumers in a transparent ecosystem.",
    },
  ];

  const benefits = [
    "End-to-end traceability for all herb batches",
    "Quality assurance through lab testing",
    "Transparent pricing and fair trade practices",
    "Sustainable sourcing documentation",
    "Consumer confidence through verification",
  ];

  return (
    <div className="min-h-screen">
      <Navigation />
      
      {/* Hero Section */}
      <motion.section
        initial="hidden"
        animate="visible"
        variants={containerVariants}
        className="hero-gradient relative overflow-hidden py-20"
        style={{
          backgroundImage: `linear-gradient(135deg, rgba(222, 204, 178, 0.9), rgba(222, 204, 178, 0.7)), url(${heroImage})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <div className="container mx-auto px-6">
          <div className="max-w-4xl mx-auto text-center">
            <motion.h1
              variants={itemVariants}
              className="text-5xl md:text-7xl font-bold text-foreground mb-6 animate-fade-in-up animate-pulse"
            >
              Trace the Purity of
              <span className="block text-primary">
                Ayurvedic Herbs
              </span>
            </motion.h1>
            
            <motion.p
              variants={itemVariants}
              className="text-xl md:text-2xl text-black mb-8 max-w-3xl mx-auto animate-slide-in-left"
            >
              From sacred groves to your wellness journey - ensuring authenticity, 
              quality, and transparency in every leaf, root, and flower.
            </motion.p>
            
            <motion.div
              variants={itemVariants}
              className="flex flex-col sm:flex-row gap-4 justify-center"
            >
              <Button 
                asChild 
                size="lg" 
                className="btn-nature text-lg px-8 py-6 animate-bounce-in rounded-lg"
              >
                <Link to="/consumer">
                  Start Tracing <ArrowRight className="ml-2 h-5 w-5" />
                </Link>
              </Button>
              
              <Button 
                variant="outline" 
                size="lg" 
                asChild
                className="text-lg px-8 py-6 border-primary text-primary hover:bg-primary hover:text-primary-foreground rounded-lg"
              >
                <Link to="/collector">
                  Join Network
                </Link>
              </Button>
            </motion.div>
          </div>
        </div>

        {/* Floating herbal elements */}
        <motion.div
          animate={{ 
            rotate: 360,
            scale: [1, 1.1, 1],
          }}
          transition={{ 
            rotate: { duration: 20, repeat: Infinity, ease: "linear" },
            scale: { duration: 4, repeat: Infinity, ease: "easeInOut" }
          }}
          className="absolute top-20 right-10 opacity-20"
        >
          <Leaf className="h-20 w-20 text-primary" />
        </motion.div>
        
        <motion.div
          animate={{ 
            rotate: -360,
            y: [0, -20, 0],
          }}
          transition={{ 
            rotate: { duration: 25, repeat: Infinity, ease: "linear" },
            y: { duration: 3, repeat: Infinity, ease: "easeInOut" }
          }}
          className="absolute bottom-20 left-10 opacity-20"
        >
          <Leaf className="h-16 w-16 text-primary" />
        </motion.div>
      </motion.section>

      {/* Features Section */}
      <motion.section
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true, amount: 0.3 }}
        variants={containerVariants}
        className="py-20"
      >
        <div className="container mx-auto px-6">
          <motion.div variants={itemVariants} className="text-center mb-16">
            <h2 className="text-4xl font-bold text-foreground mb-4">
              Why Choose AyurTrace?
            </h2>
            <p className="text-xl max-w-2xl mx-auto">
              Revolutionary technology meets ancient wisdom to ensure the highest quality 
              in Ayurvedic herb sourcing and distribution.
            </p>
          </motion.div>

          <div className="grid md:grid-cols-3 gap-8">
            {features.map((feature, index) => {
              const Icon = feature.icon;
              return (
                <motion.div
                  key={feature.title}
                  variants={itemVariants}
                  whileHover={{ y: -5, scale: 1.02 }}
                  transition={{ duration: 0.3 }}
                >
                  <Card className="nature-card h-full border-border/20">
                    <CardHeader className="text-center">
                      <motion.div
                        whileHover={{ rotate: [0, -10, 10, 0] }}
                        transition={{ duration: 0.5 }}
                        className="mx-auto mb-4 p-4 rounded-full bg-primary/20 w-fit"
                      >
                        <Icon className="h-8 w-8 text-primary" />
                      </motion.div>
                      <CardTitle className="text-xl text-card-foreground">
                        {feature.title}
                      </CardTitle>
                    </CardHeader>
                    <CardContent>
                      <CardDescription className="text-center text-card-foreground/70">
                        {feature.description}
                      </CardDescription>
                    </CardContent>
                  </Card>
                </motion.div>
              );
            })}
          </div>
        </div>
      </motion.section>

      {/* Benefits Section */}
      <motion.section
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true, amount: 0.3 }}
        variants={containerVariants}
        className="py-20 bg-muted/10"
      >
        <div className="container mx-auto px-6">
          <div className="grid lg:grid-cols-2 gap-12 items-center">
            <motion.div variants={itemVariants}>
              <h2 className="text-4xl font-bold text-foreground mb-6">
                Complete Transparency in Every Step
              </h2>
              <p className="text-lg text-muted-foreground mb-8">
                Our comprehensive traceability system ensures that every herb 
                in your wellness journey meets the highest standards of authenticity 
                and quality that Ayurveda demands.
              </p>
              
              <div className="space-y-4">
                {benefits.map((benefit, index) => (
                  <motion.div
                    key={index}
                    variants={itemVariants}
                    className="flex items-start space-x-3"
                  >
                    <CheckCircle className="h-5 w-5 text-primary mt-1 flex-shrink-0" />
                    <span className="text-foreground">{benefit}</span>
                  </motion.div>
                ))}
              </div>
            </motion.div>

            <motion.div
              variants={itemVariants}
              className="relative"
            >
              <Card className="nature-card p-8">
                <div className="text-center">
                  <motion.div
                    animate={{ 
                      rotate: [0, 5, -5, 0],
                      scale: [1, 1.05, 1]
                    }}
                    transition={{ 
                      duration: 4, 
                      repeat: Infinity, 
                      ease: "easeInOut" 
                    }}
                    className="mb-6"
                  >
                    <Leaf className="h-20 w-20 text-primary mx-auto" />
                  </motion.div>
                  <h3 className="text-2xl font-bold text-card-foreground mb-4">
                    Join the Network
                  </h3>
                  <p className="text-card-foreground/70 mb-6">
                    Whether you're a collector, processor, or consumer, 
                    become part of our trusted Ayurvedic ecosystem.
                  </p>
                  <Button 
                    asChild 
                    className="btn-nature w-full"
                  >
                    <Link to="/collector">
                      Get Started Today
                    </Link>
                  </Button>
                </div>
              </Card>
            </motion.div>
          </div>
        </div>
      </motion.section>

      <Footer />
    </div>
  );
};

export default HomePage;