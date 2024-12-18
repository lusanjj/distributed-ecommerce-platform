import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';

const Navbar = () => {
    return (
        <AppBar position="static" sx={{ background: '#ff6f61' }}>
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    MyShop
                </Typography>
                <Box>
                    <Button color="inherit" href="/">Home</Button>
                    <Button color="inherit" href="/login">Login</Button>
                    <Button color="inherit" href="/signup">Sign Up</Button>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
