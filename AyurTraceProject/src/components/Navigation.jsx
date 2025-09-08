import { useState } from "react";
import { motion } from "framer-motion";
import { Link, useLocation } from "react-router-dom";
import { Leaf, Home, Users, FlaskConical, Factory, ShoppingCart, Settings } from "lucide-react";

const Navigation = () => {
  const location = useLocation();
  const [isOpen, setIsOpen] = useState(false);

  const navItems = [
    { path: "/", icon: Home, label: "Home" },
    { path: "/collector", icon: Leaf, label: "Collector" },
    { path: "/lab", icon: FlaskConical, label: "Lab" },
    { path: "/processor", icon: Factory, label: "Processor" },
    { path: "/consumer", icon: ShoppingCart, label: "Consumer" },
    { path: "/admin", icon: Settings, label: "Admin" },
  ];

  return (
    <motion.nav
      initial={{ y: -100, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.6, ease: "easeOut" }}
      className="nav-nature sticky top-0 z-50 border-b border-border/20 bg-background"
    >
      <div className="container mx-auto px-6 py-4">
        <div className="flex items-center justify-between">
          {/* Logo */}
          <Link to="/" className="flex items-center space-x-3">
            <motion.div
              whileHover={{ rotate: 360, scale: 1.1 }}
              transition={{ duration: 0.6 }}
              className="p-2 rounded-full bg-primary/20"
            >
              <Leaf className="h-8 w-8 text-primary" />
            </motion.div>
            <h1 className="text-2xl font-bold text-card-foreground">
              AyurTrace
            </h1>
          </Link>

          {/* Navigation Links for medium and larger screens */}
          <div className="hidden md:flex items-center space-x-1">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive = location.pathname === item.path;
              return (
                <Link key={item.path} to={item.path}>
                  <motion.div
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    className={`flex items-center space-x-2 px-4 py-2 rounded-lg transition-colors ${isActive
                      ? "bg-primary text-primary-foreground shadow-glow"
                      : "text-card-foreground hover:bg-card-foreground/10"
                      }`}
                  >
                    <Icon className="h-4 w-4" />
                    <span className="text-sm font-medium">{item.label}</span>
                  </motion.div>
                </Link>
              );
            })}
          </div>

          {/* Mobile Menu Button */}
          <div className="md:hidden relative">
            <motion.button
              whileTap={{ scale: 0.9 }}
              className="p-2 text-card-foreground"
              onClick={() => setIsOpen(!isOpen)}
            >
              <Users className="h-6 w-6" />
            </motion.button>

            {isOpen && (
              <motion.div
                initial={{ opacity: 0, y: -10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.2 }}
                className="absolute right-0 mt-2 w-48 bg-[#1B2C08] 
 border border-border rounded-lg shadow-lg z-50 flex flex-col"
              >
                {navItems.map((item) => {
                  const Icon = item.icon;
                  const isActive = location.pathname === item.path;
                  return (
                    <Link
                      key={item.path}
                      to={item.path}
                      onClick={() => setIsOpen(false)}
                      className={`flex items-center space-x-2 px-4 py-3 text-sm rounded-lg transition-colors text-white  ${isActive
                          ? "bg-primary text-primary-foreground"
                          : "hover:bg-card-foreground/10 text-card-foreground"
                        }`}
                    >
                      <Icon className="h-4 w-4" />
                      <span>{item.label}</span>
                    </Link>
                  );
                })}
              </motion.div>
            )}
          </div>


        </div>
      </div>
    </motion.nav>
  );
};

export default Navigation;
