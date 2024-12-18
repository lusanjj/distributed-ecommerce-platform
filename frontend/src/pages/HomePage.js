import React from 'react';
import { Container, Typography, Button, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import HeroSection from '../components/HeroSection'; // 自定义组件
import ProductGrid from '../components/ProductGrid'; // 自定义组件

const HomePage = () => {
    const navigate = useNavigate();

    return (
        <div>
            {/* 英雄区域 */}
            <HeroSection />

            {/* 主内容 */}
            <Container maxWidth="lg" sx={{ mt: 4 }}>
                <Box sx={{ textAlign: 'center', mb: 4 }}>
                    <Typography variant="h3" gutterBottom>
                        Welcome to Our E-Commerce Platform
                    </Typography>
                    <Typography variant="body1" sx={{ mb: 2 }}>
                        Discover the best products and enjoy seamless shopping!
                    </Typography>
                    <Button
                        variant="contained"
                        color="primary"
                        size="large"
                        onClick={() => navigate('/signup')}
                    >
                        Get Started
                    </Button>
                </Box>

                {/* 产品展示 */}
                <ProductGrid />
            </Container>
        </div>
    );
};

export default HomePage;
