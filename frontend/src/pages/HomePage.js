import React from 'react';
import Navbar from '../components/Navbar';
import HeroSection from '../components/HeroSection';
import ProductGrid from '../components/ProductGrid';
import Footer from '../components/Footer';

const HomePage = () => {
    return (
        <>
            <Navbar />
            <HeroSection />
            <ProductGrid />
            <Footer />
        </>
    );
};

export default HomePage;
