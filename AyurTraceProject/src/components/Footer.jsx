import { motion } from "framer-motion";
import { Leaf, Heart, Shield, CheckCircle, Instagram, Twitter, Linkedin } from "lucide-react";

const Footer = () => {
  return (
    <motion.footer
      initial={{ y: 100, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.6, ease: "easeOut", delay: 0.2 }}
      className="nav-nature border-t border-border/20 mt-20 bg-muted/5"
    >
      <div className="container mx-auto px-6 py-12">
        <div className="flex flex-col lg:flex-row justify-between gap-12">
          
          {/* Logo */}
          <div className="flex flex-col space-y-4">
            <div className="flex items-center space-x-3">
              <motion.div
                animate={{ rotate: [0, 10, -10, 0] }}
                transition={{ duration: 2, repeat: Infinity, repeatDelay: 3 }}
                className="p-2 rounded-full bg-primary/20"
              >
                <Leaf className="h-6 w-6 text-primary" />
              </motion.div>
              <h3 className="text-lg font-semibold text-card-foreground">AyurTrace</h3>
            </div>
            <p className="text-sm text-card-foreground/70 max-w-xs">
              Tracing Ayurvedic herbs from source to shelf. Ensuring authenticity, quality, and transparency.
            </p>
          </div>

          {/* How It Works */}
          <div className="flex flex-col space-y-3">
            <h4 className="text-lg font-semibold text-card-foreground mb-2">How It Works</h4>
            <div className="flex items-center space-x-2 text-card-foreground">
              <Leaf className="h-4 w-4 text-green-400" /> <span>Collection from farm</span>
            </div>
            <div className="flex items-center space-x-2 text-card-foreground">
              <Shield className="h-4 w-4 text-green-400" /> <span>Lab testing & verification</span>
            </div>
            <div className="flex items-center space-x-2 text-card-foreground">
              <CheckCircle className="h-4 w-4 text-green-400" /> <span>Trace & consumer delivery</span>
            </div>
          </div>

          {/* Contact */}
          <div className="flex flex-col space-y-3">
            <h4 className="text-lg font-semibold text-card-foreground">Contact</h4>
            <p className="text-sm text-card-foreground/70">support@ayurtrace.com</p>
            <p className="text-sm text-card-foreground/70">+91 98765 43210</p>
            <div className="flex space-x-4 mt-2">
              <motion.div whileHover={{ scale: 1.2 }} className="cursor-pointer">
                <Instagram className="h-5 w-5 text-pink-400" />
              </motion.div>
              <motion.div whileHover={{ scale: 1.2 }} className="cursor-pointer">
                <Twitter className="h-5 w-5 text-blue-400" />
              </motion.div>
              <motion.div whileHover={{ scale: 1.2 }} className="cursor-pointer">
                <Linkedin className="h-5 w-5 text-blue-600" />
              </motion.div>
            </div>
          </div>

          {/* Made with Love */}
          <div className="flex flex-col items-start space-y-3">
            <h4 className="text-lg font-semibold text-card-foreground">Made with Love</h4>
            <div className="flex items-center space-x-2 text-card-foreground/70">
              <span className="text-sm">by our team</span>
              <motion.div
                animate={{ scale: [1, 1.2, 1] }}
                transition={{ duration: 1, repeat: Infinity, repeatDelay: 2 }}
              >
                <Heart className="h-4 w-4 text-primary fill-current" />
              </motion.div>
            </div>
          </div>
        </div>

        {/* Copyright */}
        <div className="border-t border-border/20 mt-8 pt-6 text-center">
          <p className="text-sm text-card-foreground/60">
            © 2024 AyurTrace. Preserving the authenticity of Ayurvedic traditions.
          </p>
        </div>
      </div>
    </motion.footer>
  );
};

export default Footer;
