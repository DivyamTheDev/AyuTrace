import { useState } from "react";
import { Leaf, Shield, Zap, Users, ArrowRight, CheckCircle, Eye, Award, Truck, FlaskConical, Star, Globe, Microscope } from "lucide-react";
import leaves from '../assets/leaves.jpg';
import processing from '../assets/processing.jpg';

const HomePage = () => {
  const [activeTab, setActiveTab] = useState(0);

  const features = [
    {
      icon: Shield,
      title: "Authentic Verification",
      description: "Blockchain-based verification ensures genuine Ayurvedic herbs from farm to pharmacy.",
      image: "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400&h=300&fit=crop&crop=center"
    },
    {
      icon: Zap,
      title: "Real-time Tracking",
      description: "Track your herbs journey through collection, testing, processing, and distribution.",
      image: "https://images.unsplash.com/photo-1559757148-5c350d0d3c56?w=400&h=300&fit=crop&crop=center"
    },
    {
      icon: Users,
      title: "Trusted Network",
      description: "Connect collectors, labs, processors, and consumers in a transparent ecosystem.",
      image: "https://images.unsplash.com/photo-1582750433449-648ed127bb54?w=400&h=300&fit=crop&crop=center"
    },
  ];

  const processSteps = [
    {
      icon: Leaf,
      title: "Collection",
      description: "Sacred herbs collected from certified organic farms",
      image: "https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=300&h=200&fit=crop&crop=center",
      details: "Expert collectors identify and harvest herbs at optimal times using traditional methods."
    },
    {
      icon: FlaskConical,
      title: "Testing",
      description: "Rigorous lab testing for purity and potency",
      image: "https://images.unsplash.com/photo-1582719471384-894fbb16e074?w=300&h=200&fit=crop&crop=center",
      details: "Advanced laboratory analysis ensures each batch meets strict quality standards."
    },
    {
      icon: Award,
      title: "Processing",
      description: "Traditional methods meet modern quality standards",
      image: `${processing}`,
      details: "Time-honored Ayurvedic processing techniques enhanced with modern quality control."
    },
    {
      icon: FlaskConical,
      title: "Testing",
      description: "Rigorous lab testing for purity and potency",
      image: "https://images.unsplash.com/photo-1582719471384-894fbb16e074?w=300&h=200&fit=crop&crop=center",
      details: "Advanced laboratory analysis ensures each batch meets strict quality standards."
    },
    {
      icon: Truck,
      title: "Distribution",
      description: "Secure delivery maintaining herb integrity",
      image: "https://images.unsplash.com/photo-1566576912321-d58ddd7a6088?w=300&h=200&fit=crop&crop=center",
      details: "Temperature-controlled logistics ensure herbs reach consumers in perfect condition."
    }
  ];

  const galleryImages = [
    {
      url: "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=600&h=400&fit=crop&crop=center",
      title: "Ayurvedic Garden",
      description: "Organic herb cultivation"
    },
    {
      url: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=600&h=400&fit=crop&crop=center",
      title: "Traditional Herbs",
      description: "Premium quality selection"
    },
    {
      url: "https://images.unsplash.com/photo-1606914469633-e06b7b1623f8?w=600&h=400&fit=crop&crop=center",
      title: "Quality Control",
      description: "Rigorous testing protocols"
    },
    {
      url: "https://images.unsplash.com/photo-1542736667-069246bdbc6d?w=600&h=400&fit=crop&crop=center",
      title: "Sustainable Sourcing",
      description: "Ethical and eco-friendly practices"
    }
  ];

  const stats = [
    { value: "10,000+", label: "Herbs Traced", icon: Leaf },
    { value: "500+", label: "Network Partners", icon: Users },
    { value: "99.9%", label: "Accuracy Rate", icon: Shield },
    { value: "50+", label: "Countries Served", icon: Globe }
  ];

  const benefits = [
    "End-to-end traceability for all herb batches",
    "Quality assurance through lab testing",
    "Transparent pricing and fair trade practices",
    "Sustainable sourcing documentation",
    "Consumer confidence through verification",
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-green-50 to-white">
      {/* Navigation */}
      <nav className="bg-white/95 backdrop-blur-sm border-b shadow-sm sticky top-0 z-50">
        <div className="container mx-auto px-6 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-2">
              <Leaf className="h-8 w-8 text-green-600" />
              <span className="text-2xl font-bold text-gray-900">AyurTrace</span>
            </div>
            <div className="hidden md:flex space-x-8">
              <a href="#home" className="text-gray-700 hover:text-green-600 transition-colors">Home</a>
              <a href="#features" className="text-gray-700 hover:text-green-600 transition-colors">Features</a>
              <a href="#process" className="text-gray-700 hover:text-green-600 transition-colors">Process</a>
              <a href="#gallery" className="text-gray-700 hover:text-green-600 transition-colors">Gallery</a>
            </div>
            <button className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors">
              Get Started
            </button>
          </div>
        </div>
      </nav>
      
      {/* Hero Section */}
      <section 
        id="home"
        className="relative overflow-hidden py-20 bg-gradient-to-r from-emerald-900/90 to-green-800/90"
        style={{
          backgroundImage: `linear-gradient(120deg, rgba(6, 78, 59, 0.9), rgba(5, 46, 22, 0.8)), url('${leaves}')`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <div className="container mx-auto px-6">
          <div className="grid lg:grid-cols-2 gap-12 items-center">
            <div className="text-white">
              <h1 className="text-5xl md:text-7xl font-bold mb-6">
                Trace the Purity of
                <span className="block text-green-300">
                  Ayurvedic Herbs
                </span>
              </h1>
              
              <p className="text-xl md:text-2xl mb-8 text-green-100">
                From sacred groves to your wellness journey - ensuring authenticity, 
                quality, and transparency in every leaf, root, and flower.
              </p>
              
              <div className="flex flex-col sm:flex-row gap-4">
                <button className="bg-green-600 hover:bg-green-700 text-white text-lg py-3 px-8 rounded-lg shadow-2xl transform hover:scale-105 transition-all duration-300 flex items-center justify-center">
                  Start Tracing <ArrowRight className="ml-2 h-5 w-5" />
                </button>
                
                <button className="text-lg px-8 py-3 border-2 border-green-300 text-green-300 hover:bg-green-300 hover:text-green-900 rounded-lg backdrop-blur-sm transition-all duration-300">
                  Join Network
                </button>
              </div>
            </div>

            {/* Hero Image Gallery */}
            <div className="grid grid-cols-2 gap-4 relative">
              <div className="relative overflow-hidden rounded-2xl shadow-2xl transform hover:scale-105 transition-all duration-300">
                <img 
                  src="https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=300&h=400&fit=crop&crop=center"
                  alt="Fresh Ayurvedic herbs"
                  className="w-full h-64 object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent" />
                <div className="absolute bottom-4 left-4 text-white">
                  <p className="font-semibold">Fresh Herbs</p>
                </div>
              </div>

              <div className="relative overflow-hidden rounded-2xl shadow-2xl mt-8 transform hover:scale-105 transition-all duration-300">
                <img 
                  src={`${processing}`}
                  alt="Herb processing"
                  className="w-full h-64 object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent" />
                <div className="absolute bottom-4 left-4 text-white">
                  <p className="font-semibold">Processing</p>
                </div>
              </div>

              <div className="relative overflow-hidden rounded-2xl shadow-2xl col-span-2 -mt-4 transform hover:scale-105 transition-all duration-300">
                <img 
                  src="https://images.unsplash.com/photo-1582719471384-894fbb16e074?w=600&h=200&fit=crop&crop=center"
                  alt="Lab testing"
                  className="w-full h-32 object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent" />
                <div className="absolute bottom-4 left-4 text-white">
                  <p className="font-semibold">Quality Testing</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Floating Elements */}
        <div className="absolute top-20 right-10 opacity-20 animate-spin" style={{animationDuration: '20s'}}>
          <Leaf className="h-20 w-20 text-green-300" />
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-16 bg-white">
        <div className="container mx-auto px-6">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            {stats.map((stat, index) => {
              const Icon = stat.icon;
              return (
                <div key={index} className="text-center group hover:scale-105 transition-transform duration-300">
                  <div className="bg-green-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4 group-hover:bg-green-200 transition-colors">
                    <Icon className="h-8 w-8 text-green-600" />
                  </div>
                  <div className="text-3xl font-bold text-gray-900 mb-2">{stat.value}</div>
                  <div className="text-gray-600">{stat.label}</div>
                </div>
              );
            })}
          </div>
        </div>
      </section>

      {/* Process Steps Section */}
      <section id="process" className="py-20 bg-green-50">
        <div className="container mx-auto px-6">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">
              Our Traceability Process
            </h2>
            <p className="text-xl text-gray-600 max-w-2xl mx-auto">
              Follow the complete journey of your Ayurvedic herbs from source to shelf
            </p>
          </div>

          <div className="grid md:grid-cols-5 gap-8">
            {processSteps.map((step, index) => {
              const Icon = step.icon;
              return (
                <div key={step.title} className="text-center group">
                  <div className="relative mb-6">
                    <div className="relative overflow-hidden rounded-2xl shadow-lg group-hover:shadow-2xl transition-all duration-300">
                      <img 
                        src={step.image}
                        alt={step.title}
                        className="w-full h-48 object-cover transition-transform duration-300 group-hover:scale-110"
                      />
                      <div className="absolute inset-0 bg-gradient-to-t from-green-900/80 to-transparent" />
                      <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2">
                        <div className="bg-white/20 backdrop-blur-sm rounded-full p-3 group-hover:bg-white/30 transition-all duration-300">
                          <Icon className="h-8 w-8 text-white" />
                        </div>
                      </div>
                    </div>
                    <div className="absolute -top-2 -right-2 bg-green-600 text-white rounded-full w-8 h-8 flex items-center justify-center font-bold text-sm">
                      {index + 1}
                    </div>
                  </div>
                  <h3 className="text-xl font-bold text-gray-900 mb-2">{step.title}</h3>
                  <p className="text-gray-600 mb-3">{step.description}</p>
                  <p className="text-sm text-gray-500 italic">{step.details}</p>
                </div>
              );
            })}
          </div>
        </div>
      </section>

      {/* Features Section with Images */}
      <section id="features" className="py-20 bg-white">
        <div className="container mx-auto px-6">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">
              Why Choose AyurTrace?
            </h2>
            <p className="text-xl text-gray-600 max-w-2xl mx-auto">
              Revolutionary technology meets ancient wisdom to ensure the highest quality 
              in Ayurvedic herb sourcing and distribution.
            </p>
          </div>

          <div className="grid md:grid-cols-3 gap-8">
            {features.map((feature, index) => {
              const Icon = feature.icon;
              return (
                <div key={feature.title} className="group hover:-translate-y-2 transition-all duration-300">
                  <div className="bg-white h-full border-0 shadow-xl hover:shadow-2xl transition-all duration-300 overflow-hidden rounded-2xl">
                    <div className="relative">
                      <img 
                        src={feature.image}
                        alt={feature.title}
                        className="w-full h-48 object-cover group-hover:scale-110 transition-transform duration-500"
                      />
                      <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent" />
                      <div className="absolute bottom-4 right-4 p-3 rounded-full bg-white/20 backdrop-blur-sm group-hover:bg-white/30 transition-all duration-300">
                        <Icon className="h-8 w-8 text-white" />
                      </div>
                    </div>
                    <div className="p-6">
                      <h3 className="text-xl font-bold text-gray-900 mb-3">
                        {feature.title}
                      </h3>
                      <p className="text-gray-600">
                        {feature.description}
                      </p>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </section>

      {/* Gallery Section */}
      <section id="gallery" className="py-20 bg-gray-50">
        <div className="container mx-auto px-6">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">
              Our Herb Journey Gallery
            </h2>
            <p className="text-xl text-gray-600">
              Explore the beauty and authenticity of our Ayurvedic herb ecosystem
            </p>
          </div>

          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            {galleryImages.map((image, index) => (
              <div key={index} className="group relative overflow-hidden rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-500">
                <img 
                  src={image.url}
                  alt={image.title}
                  className="w-full h-64 object-cover group-hover:scale-110 transition-transform duration-500"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300" />
                <div className="absolute bottom-0 left-0 right-0 p-6 text-white transform translate-y-full group-hover:translate-y-0 transition-transform duration-300">
                  <h3 className="text-lg font-semibold mb-2">{image.title}</h3>
                  <p className="text-sm text-gray-200">{image.description}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Benefits Section with Visual Elements */}
      <section className="py-20 bg-white">
        <div className="container mx-auto px-6">
          <div className="grid lg:grid-cols-2 gap-12 items-center">
            <div>
              <h2 className="text-4xl font-bold text-gray-900 mb-6">
                Complete Transparency in Every Step
              </h2>
              <p className="text-lg text-gray-600 mb-8">
                Our comprehensive traceability system ensures that every herb 
                in your wellness journey meets the highest standards of authenticity 
                and quality that Ayurveda demands.
              </p>
              
              <div className="space-y-4">
                {benefits.map((benefit, index) => (
                  <div key={index} className="flex items-start space-x-3 p-4 rounded-lg bg-green-50 hover:bg-green-100 transition-colors duration-300 group">
                    <CheckCircle className="h-6 w-6 text-green-600 mt-0.5 flex-shrink-0 group-hover:scale-110 transition-transform duration-300" />
                    <span className="text-gray-800 font-medium">{benefit}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="relative">
              <div className="grid grid-cols-2 gap-4">
                <div className="relative overflow-hidden rounded-2xl shadow-lg hover:scale-105 transition-transform duration-300">
                  <img 
                    src="https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=300&fit=crop&crop=center"
                    alt="Ayurvedic herbs collection"
                    className="w-full h-40 object-cover"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-green-900/50 to-transparent" />
                </div>

                <div className="relative overflow-hidden rounded-2xl shadow-lg mt-8 hover:scale-105 transition-transform duration-300">
                  <img 
                    src="https://images.unsplash.com/photo-1606914469633-e06b7b1623f8?w=300&h=300&fit=crop&crop=center"
                    alt="Quality control"
                    className="w-full h-40 object-cover"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-green-900/50 to-transparent" />
                </div>

                <div className="relative overflow-hidden rounded-2xl shadow-lg col-span-2 -mt-4 hover:scale-105 transition-transform duration-300">
                  <img 
                    src="https://images.unsplash.com/photo-1542736667-069246bdbc6d?w=600&h=200&fit=crop&crop=center"
                    alt="Sustainable sourcing"
                    className="w-full h-32 object-cover"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-green-900/50 to-transparent" />
                  <div className="absolute bottom-4 left-4 text-white">
                    <p className="font-semibold">Sustainable & Ethical Sourcing</p>
                  </div>
                </div>
              </div>

              <div className="absolute -top-4 -right-4 bg-green-600 rounded-full p-4 shadow-2xl animate-pulse">
                <Leaf className="h-12 w-12 text-white" />
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Interactive Testimonials */}
      <section className="py-20 bg-green-50">
        <div className="container mx-auto px-6">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">
              Trusted by Industry Leaders
            </h2>
            <p className="text-xl text-gray-600">
              See what our network partners say about AyurTrace
            </p>
          </div>

          <div className="grid md:grid-cols-3 gap-8">
            {[
              {
                name: "Dr. Priya Sharma",
                role: "Ayurvedic Practitioner",
                image: "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=100&h=100&fit=crop&crop=face",
                quote: "AyurTrace has revolutionized how I source authentic herbs for my practice."
              },
              {
                name: "Raj Kumar",
                role: "Herb Collector",
                image: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop&crop=face",
                quote: "The transparency and fair pricing have transformed our traditional farming business."
              },
              {
                name: "Sarah Wilson",
                role: "Wellness Consumer",
                image: "https://images.unsplash.com/photo-1494790108755-2616b612b742?w=100&h=100&fit=crop&crop=face",
                quote: "I finally have confidence in the quality and authenticity of my herbal supplements."
              }
            ].map((testimonial, index) => (
              <div key={index} className="bg-white p-8 rounded-2xl shadow-lg hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2">
                <div className="flex items-center mb-4">
                  {[...Array(5)].map((_, i) => (
                    <Star key={i} className="h-5 w-5 text-yellow-400 fill-current" />
                  ))}
                </div>
                <p className="text-gray-700 mb-6 italic">"{testimonial.quote}"</p>
                <div className="flex items-center">
                  <img 
                    src={testimonial.image}
                    alt={testimonial.name}
                    className="w-12 h-12 rounded-full mr-4 object-cover"
                  />
                  <div>
                    <h4 className="font-semibold text-gray-900">{testimonial.name}</h4>
                    <p className="text-gray-600 text-sm">{testimonial.role}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Call to Action Section */}
      <section className="py-20 relative overflow-hidden"
        style={{
          backgroundImage: `linear-gradient(135deg, rgba(5, 150, 105, 0.95), rgba(16, 185, 129, 0.9)), url('https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=1200&h=600&fit=crop&crop=center')`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}
      >
        <div className="container mx-auto px-6 text-center">
          <div className="max-w-3xl mx-auto text-white">
            <h2 className="text-4xl font-bold mb-6">
              Ready to Join the Ayurvedic Revolution?
            </h2>
            <p className="text-xl mb-8 text-green-100">
              Experience the future of herbal traceability and become part of our trusted network today.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <button className="bg-white text-green-800 hover:bg-green-50 text-lg py-3 px-8 rounded-lg shadow-2xl transform hover:scale-105 transition-all duration-300 flex items-center justify-center">
                <Eye className="mr-2 h-5 w-5" />
                Start Tracing Now
              </button>
              
              <button className="text-lg px-8 py-3 border-2 border-white text-white hover:bg-white hover:text-green-800 rounded-lg backdrop-blur-sm transition-all duration-300 flex items-center justify-center">
                <Users className="mr-2 h-5 w-5" />
                Join Our Network
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-white py-16">
        <div className="container mx-auto px-6">
          <div className="grid md:grid-cols-4 gap-8">
            <div>
              <div className="flex items-center space-x-2 mb-4">
                <Leaf className="h-8 w-8 text-green-400" />
                <span className="text-2xl font-bold">AyurTrace</span>
              </div>
              <p className="text-gray-400">
                Revolutionizing Ayurvedic herb traceability through blockchain technology.
              </p>
            </div>
            
            <div>
              <h3 className="text-lg font-semibold mb-4">Products</h3>
              <ul className="space-y-2 text-gray-400">
                <li><a href="#" className="hover:text-white transition-colors">Herb Tracking</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Quality Assurance</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Supply Chain</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="text-lg font-semibold mb-4">Company</h3>
              <ul className="space-y-2 text-gray-400">
                <li><a href="#" className="hover:text-white transition-colors">About Us</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Careers</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Contact</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="text-lg font-semibold mb-4">Support</h3>
              <ul className="space-y-2 text-gray-400">
                <li><a href="#" className="hover:text-white transition-colors">Help Center</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Documentation</a></li>
                <li><a href="#" className="hover:text-white transition-colors">API</a></li>
              </ul>
            </div>
          </div>
          
          <div className="border-t border-gray-800 mt-12 pt-8 text-center text-gray-400">
            <p>&copy; 2025 AyurTrace. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div> 
  );
};
export default HomePage;