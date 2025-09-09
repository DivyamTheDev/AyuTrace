import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AnimatePresence } from "framer-motion";
import CollectorDashboard from "./pages/CollectorDashboard";
import LabDashboard from "./pages/LabDashboard";
import ProcessorDashboard from "./pages/ProcessorDashboard";
import ConsumerPortal from "./pages/ConsumerPortal";
import AdminDashboard from "./pages/AdminDashboard";
import NotFound from "./pages/NotFound";
import HomePage from "./pages/HomePage";
import LoginForm from "./pages/LoginPage";
import SignupForm from "./pages/SignupPage";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <AnimatePresence mode="wait">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/collector" element={<CollectorDashboard />} />
            <Route path="/lab" element={<LabDashboard />} />
            <Route path="/processor" element={<ProcessorDashboard />} />
            <Route path="/consumer" element={<ConsumerPortal />} />
            <Route path="/admin" element={<AdminDashboard />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/signup" element={<SignupForm />} />
            {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </AnimatePresence>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;