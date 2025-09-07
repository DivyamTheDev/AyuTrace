import { motion } from "framer-motion";
import { Leaf, Heart } from "lucide-react";

const Footer = () => {
  return (
    <motion.footer
      initial={{ y: 100, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.6, ease: "easeOut", delay: 0.2 }}
      className="nav-nature border-t border-border/20 mt-20"
    >
      <div className="container mx-auto px-6 py-8">
        <div className="flex flex-col md:flex-row items-center justify-between">
          {/* Logo and Description */}
          <div className="flex items-center space-x-3 mb-4 md:mb-0">
            <motion.div
              animate={{ rotate: [0, 10, -10, 0] }}
              transition={{ duration: 2, repeat: Infinity, repeatDelay: 3 }}
              className="p-2 rounded-full bg-primary/20"
            >
              <Leaf className="h-6 w-6 text-primary" />
            </motion.div>
            <div>
              <h3 className="text-lg font-semibold text-card-foreground">AyurTrace</h3>
              <p className="text-sm text-card-foreground/70">
                Tracing Ayurvedic herbs from source to shelf
              </p>
            </div>
          </div>

          {/* Made with Love */}
          <div className="flex items-center space-x-2 text-card-foreground/70">
            <span className="text-sm">Made with</span>
            <motion.div
              animate={{ scale: [1, 1.2, 1] }}
              transition={{ duration: 1, repeat: Infinity, repeatDelay: 2 }}
            >
              <Heart className="h-4 w-4 text-primary fill-current" />
            </motion.div>
            <span className="text-sm">for natural wellness</span>
          </div>
        </div>

        {/* Copyright */}
        <div className="border-t border-border/20 mt-6 pt-6 text-center">
          <p className="text-sm text-card-foreground/60">
            © 2024 AyurTrace. Preserving the authenticity of Ayurvedic traditions.
          </p>
        </div>
      </div>
    </motion.footer>
  );
};

export default Footer;