import React, { useState } from 'react';
import {
    TextField,
    Button,
    Container,
    Typography,
    Box,
    Snackbar,
    Alert,
    IconButton,
    InputAdornment,
    CircularProgress,
    Grid,
} from '@mui/material';
import { Visibility, VisibilityOff, ArrowBack } from '@mui/icons-material';
import axios from '../services/api';
import { useNavigate } from 'react-router-dom';
import '../styles/SignUpPage.css';

const SignUpPage = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    });
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [loading, setLoading] = useState(false);
    const [toast, setToast] = useState({ open: false, type: 'success', message: '' });
    const navigate = useNavigate();

    // 输入框变化
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    // 显示/隐藏密码
    const togglePasswordVisibility = () => setShowPassword((prev) => !prev);
    const toggleConfirmPasswordVisibility = () => setShowConfirmPassword((prev) => !prev);

    // 提交注册表单
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        if (formData.password !== formData.confirmPassword) {
            setToast({ open: true, type: 'error', message: 'Passwords do not match.' });
            setLoading(false);
            return;
        }

        try {
            await axios.post('/auth/register', {
                username: formData.username.trim(),
                email: formData.email.trim(),
                password: formData.password.trim(),
            });

            setToast({ open: true, type: 'success', message: 'Registration successful! Redirecting...' });
            setTimeout(() => navigate('/login'), 2000);
        } catch (err) {
            const errorMessage = err.response?.data?.message || 'Registration failed. Please try again.';
            setToast({ open: true, type: 'error', message: errorMessage });
        } finally {
            setLoading(false);
        }
    };

    // 返回主页
    const handleReturnToHome = () => navigate('/');

    return (
        <div className="signup-page">
            <Container maxWidth="sm" className="signup-container">
                <Box className="signup-box" sx={{ p: 4, boxShadow: 5, borderRadius: 3, position: 'relative' }}>
                    {/* 返回按钮 */}
                    <IconButton onClick={handleReturnToHome} sx={{ position: 'absolute', top: 8, left: 8 }}>
                        <ArrowBack fontSize="large" color="primary" />
                    </IconButton>

                    <Typography variant="h4" align="center" gutterBottom>
                        Create Your Account
                    </Typography>

                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={2}>
                            {/* 用户名 */}
                            <Grid item xs={12}>
                                <TextField
                                    label="Username"
                                    name="username"
                                    value={formData.username}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                />
                            </Grid>

                            {/* 邮箱 */}
                            <Grid item xs={12}>
                                <TextField
                                    label="Email"
                                    name="email"
                                    type="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                />
                            </Grid>

                            {/* 密码 */}
                            <Grid item xs={12}>
                                <TextField
                                    label="Password"
                                    name="password"
                                    type={showPassword ? 'text' : 'password'}
                                    value={formData.password}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position="end">
                                                <IconButton onClick={togglePasswordVisibility}>
                                                    {showPassword ? <VisibilityOff /> : <Visibility />}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            </Grid>

                            {/* 确认密码 */}
                            <Grid item xs={12}>
                                <TextField
                                    label="Confirm Password"
                                    name="confirmPassword"
                                    type={showConfirmPassword ? 'text' : 'password'}
                                    value={formData.confirmPassword}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                    InputProps={{
                                        endAdornment: (
                                            <InputAdornment position="end">
                                                <IconButton onClick={toggleConfirmPasswordVisibility}>
                                                    {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            </Grid>
                        </Grid>

                        {/* 提交按钮 */}
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                            sx={{ mt: 2, py: 1.2 }}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} color="inherit" /> : 'Sign Up'}
                        </Button>
                    </form>
                </Box>

                {/* Toast 提示 */}
                <Snackbar
                    open={toast.open}
                    autoHideDuration={4000}
                    onClose={() => setToast({ ...toast, open: false })}
                    anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                >
                    <Alert severity={toast.type} onClose={() => setToast({ ...toast, open: false })}>
                        {toast.message}
                    </Alert>
                </Snackbar>
            </Container>
        </div>
    );
};

export default SignUpPage;
