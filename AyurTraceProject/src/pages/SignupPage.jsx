import { motion } from "framer-motion";
import { UserPlus, Mail, Lock } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Link } from "react-router-dom";

const SignupForm = () => {
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: { opacity: 1, transition: { duration: 0.5 } },
  };

  const itemVariants = {
    hidden: { y: 20, opacity: 0 },
    visible: { y: 0, opacity: 1, transition: { duration: 0.5, ease: "easeOut" } },
  };

  return (
    <motion.div
      className="flex justify-center items-center min-h-screen px-4"
      initial="hidden"
      animate="visible"
      variants={containerVariants}
    >
      <motion.div variants={itemVariants} className="max-w-md w-full space-y-8">
        <Card className="p-8 bg-[#5f3c25] border-border/20">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl text-white">Create an Account</CardTitle>
          </CardHeader>
          <CardContent>
            <form className="space-y-6">
              <div>
                <label htmlFor="name" className="block text-sm font-medium text-white">
                  Full Name
                </label>
                <div className="mt-1 relative rounded-md shadow-sm">
                  <input
                    id="name"
                    name="name"
                    type="text"
                    required
                    className="block w-full rounded-md border border-gray-300 px-4 py-2 text-black focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary"
                    placeholder="Your name"
                  />
                  <UserPlus className="absolute right-3 top-3 h-5 w-5 text-gray-400" />
                </div>
              </div>

              <div>
                <label htmlFor="email" className="block text-sm font-medium text-white">
                  Email
                </label>
                <div className="mt-1 relative rounded-md shadow-sm">
                  <input
                    id="email"
                    name="email"
                    type="email"
                    required
                    className="block w-full rounded-md border border-gray-300 px-4 py-2 text-black focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary"
                    placeholder="you@example.com"
                  />
                  <Mail className="absolute right-3 top-3 h-5 w-5 text-gray-400" />
                </div>
              </div>

              <div>
                <label htmlFor="password" className="block text-sm font-medium text-white">
                  Password
                </label>
                <div className="mt-1 relative rounded-md shadow-sm">
                  <input
                    id="password"
                    name="password"
                    type="password"
                    required
                    className="block w-full rounded-md border border-gray-300 px-4 py-2 text-black focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary"
                    placeholder="••••••••"
                  />
                  <Lock className="absolute right-3 top-3 h-5 w-5 text-gray-400" />
                </div>
              </div>

              <Button type="submit" className="w-full bg-primary hover:bg-primary/90 text-white">
                Sign Up
              </Button>

              <div className="text-center text-white">
                Already have an account?{" "}
                <Link to="/login" className="text-blue-200 hover:underline">
                  Login
                </Link>
              </div>
            </form>
          </CardContent>
        </Card>
      </motion.div>
    </motion.div>
  );
};

export default SignupForm;
