import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Leaf, MapPin, Calendar, Weight, Plus, Check } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import Navigation from "@/components/Navigation";
import Footer from "@/components/Footer";

const CollectorDashboard = () => {
  const [collections, setCollections] = useState([
    {
      id: 1,
      herbName: "Ashwagandha",
      location: "Kerala Hills",
      date: "2024-01-15",
      quantity: "25 kg",
      status: "Verified",
    },
    {
      id: 2,
      herbName: "Turmeric",
      location: "Tamil Nadu",
      date: "2024-01-12",
      quantity: "40 kg", 
      status: "Pending",
    },
  ]);

  const [formData, setFormData] = useState({
    herbName: "",
    location: "",
    quantity: "",
    notes: "",
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1500));
    
    const newCollection = {
      id: collections.length + 1,
      herbName: formData.herbName,
      location: formData.location,
      date: new Date().toISOString().split('T')[0],
      quantity: formData.quantity,
      status: "Pending",
    };
    
    setCollections([newCollection, ...collections]);
    setFormData({ herbName: "", location: "", quantity: "", notes: "" });
    setIsSubmitting(false);
    setShowSuccess(true);
    
    setTimeout(() => setShowSuccess(false), 3000);
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
            Collector Dashboard
          </h1>
          <p className="text-xl ">
            Record and manage your herb collection activities
          </p>
        </motion.div>

        <div className="grid lg:grid-cols-3 gap-8">
          {/* Collection Form */}
          <motion.div variants={itemVariants} className="lg:col-span-1">
            <Card className="nature-card sticky top-24">
              <CardHeader>
                <CardTitle className="flex items-center text-card-foreground">
                  <Leaf className="h-5 w-5 mr-2 text-primary" />
                  New Collection
                </CardTitle>
                <CardDescription className="text-card-foreground/70">
                  Record details of your herb collection
                </CardDescription>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleSubmit} className="space-y-4">
                  <div className="space-y-2">
                    <Label htmlFor="herbName" className="text-card-foreground">
                      Herb Name
                    </Label>
                    <motion.div
                      whileFocus={{ scale: 1.02 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Input
                        id="herbName"
                        value={formData.herbName}
                        onChange={(e) => setFormData({ ...formData, herbName: e.target.value })}
                        className="placeholder-gray-400 text-black bg-gray-200 rounded-sm border-border/30 focus:border-primary focus:ring-primary/20"
                        placeholder="e.g., Ashwagandha"
                        required
                      />
                    </motion.div>
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="location" className="text-card-foreground">
                      Collection Location
                    </Label>
                    <motion.div
                      whileFocus={{ scale: 1.02 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Input
                        id="location"
                        value={formData.location}
                        onChange={(e) => setFormData({ ...formData, location: e.target.value })}
                        className="placeholder-gray-400 text-black bg-gray-200 rounded-sm border-border/30 focus:border-primary focus:ring-primary/20"
                        placeholder="e.g., Kerala Hills"
                        required
                      />
                    </motion.div>
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="quantity" className="text-card-foreground">
                      Quantity Collected
                    </Label>
                    <motion.div
                      whileFocus={{ scale: 1.02 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Input
                        id="quantity"
                        value={formData.quantity}
                        onChange={(e) => setFormData({ ...formData, quantity: e.target.value })}
                        className="placeholder-gray-400 text-black bg-gray-200 rounded-sm border-border/30 focus:border-primary focus:ring-primary/20"
                        placeholder="e.g., 25 kg"
                        required
                      />
                    </motion.div>
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="notes" className="text-card-foreground">
                      Collection Notes
                    </Label>
                    <motion.div
                      whileFocus={{ scale: 1.02 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Textarea
                        id="notes"
                        value={formData.notes}
                        onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
                        className="placeholder-gray-400 text-black bg-gray-200 rounded-sm border-border/30 focus:border-primary focus:ring-primary/20"
                        placeholder="Weather conditions, quality observations..."
                        rows={3}
                      />
                    </motion.div>
                  </div>

                  <motion.div
                    whileHover={{ scale: 1.02 }}
                    whileTap={{ scale: 0.98 }}
                  >
                    <Button
                      type="submit"
                      className="w-full btn-nature"
                      disabled={isSubmitting}
                    >
                      {isSubmitting ? (
                        <motion.div
                          animate={{ rotate: 360 }}
                          transition={{ duration: 1, repeat: Infinity, ease: "linear" }}
                          className="h-4 w-4 border-2 border-current border-t-transparent rounded-full"
                        />
                      ) : (
                        <>
                          <Plus className="h-4 w-4 mr-2" />
                          Record Collection
                        </>
                      )}
                    </Button>
                  </motion.div>
                </form>
              </CardContent>
            </Card>
          </motion.div>

          {/* Collections List */}
          <div className="lg:col-span-2">
            <motion.div variants={itemVariants} className="mb-6">
              <h2 className="text-2xl font-bold text-foreground mb-2">
                Recent Collections
              </h2>
              <p className="">
                Track and manage your herb collection history
              </p>
            </motion.div>

            <div className="space-y-4">
              <AnimatePresence>
                {collections.map((collection, index) => (
                  <motion.div
                    key={collection.id}
                    initial={{ x: 100, opacity: 0 }}
                    animate={{ x: 0, opacity: 1 }}
                    exit={{ x: -100, opacity: 0 }}
                    transition={{ 
                      duration: 0.5, 
                      delay: index * 0.1,
                      ease: "easeOut" 
                    }}
                    whileHover={{ scale: 1.02 }}
                  >
                    <Card className="nature-card border-border/20">
                      <CardContent className="p-6">
                        <div className="flex items-center justify-between mb-4">
                          <h3 className="text-xl font-semibold text-card-foreground">
                            {collection.herbName}
                          </h3>
                          <motion.span
                            animate={{ 
                              backgroundColor: collection.status === 'Verified' 
                                ? 'hsl(var(--success))' 
                                : 'hsl(var(--primary))'
                            }}
                            className={`px-3 py-1 rounded-full text-sm font-medium text-white`}
                          >
                            {collection.status}
                          </motion.span>
                        </div>
                        
                        <div className="grid md:grid-cols-3 gap-4 text-sm text-card-foreground/70">
                          <div className="flex items-center">
                            <MapPin className="h-4 w-4 mr-2 text-primary" />
                            {collection.location}
                          </div>
                          <div className="flex items-center">
                            <Calendar className="h-4 w-4 mr-2 text-primary" />
                            {collection.date}
                          </div>
                          <div className="flex items-center">
                            <Weight className="h-4 w-4 mr-2 text-primary" />
                            {collection.quantity}
                          </div>
                        </div>
                      </CardContent>
                    </Card>
                  </motion.div>
                ))}
              </AnimatePresence>
            </div>
          </div>
        </div>

        {/* Success Message */}
        <AnimatePresence>
          {showSuccess && (
            <motion.div
              initial={{ scale: 0, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0, opacity: 0 }}
              className="fixed top-4 right-4 z-50"
            >
              <Card className="bg-success text-success-foreground shadow-glow">
                <CardContent className="p-4">
                  <div className="flex items-center">
                    <Check className="h-5 w-5 mr-2" />
                    Collection recorded successfully!
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

export default CollectorDashboard;