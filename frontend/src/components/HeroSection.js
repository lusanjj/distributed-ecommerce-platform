import React from 'react';
import { Box, Typography, Button } from '@mui/material';

const HeroSection = () => {
    return (
        <Box
            sx={{
                height: '50vh',
                background: 'linear-gradient(45deg, #ff9966, #ff6f61)',
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                color: 'white',
                textAlign: 'center',
            }}
        >
            <Typography variant="h3" gutterBottom>
                Welcome to MyShop!
            </Typography>
            <Typography variant="h6" gutterBottom>
                Discover amazing products at unbeatable prices.
            </Typography>
            <Button variant="contained" color="secondary" sx={{ mt: 3 }}>
                Shop Now
            </Button>
        </Box>
    );
};

export default HeroSection;
