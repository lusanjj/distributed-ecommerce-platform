import React, { useState } from 'react';
import {
    TextField,
    Button,
    Container,
    Typography,
    Box,
    CircularProgress,
    IconButton,
    Grid,
    Alert,
    InputAdornment,
} from '@mui/material';
import { ArrowBack, Visibility, VisibilityOff } from '@mui/icons-material';
import { useDispatch } from 'react-redux';
import { setAuth, setRole } from '../redux/authSlice';
import axios from '../services/api';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import '../styles/LoginPage.css';

const LoginPage = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [showPassword, setShowPassword] = useState(false); // 控制密码显示状态
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    // 处理输入框变化
    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredentials((prev) => ({ ...prev, [name]: value }));
    };

    // 显示/隐藏密码
    const togglePasswordVisibility = () => {
        setShowPassword((prev) => !prev);
    };

    // 表单提交处理
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            const response = await axios.post('/auth/login', credentials);
            const accessToken = response?.data?.data?.accessToken;
            const refreshToken = response?.data?.data?.refreshToken;

            if (!accessToken || !refreshToken) {
                throw new Error('Server did not return tokens.');
            }

            const decoded = jwtDecode(accessToken);
            dispatch(setAuth(accessToken));
            dispatch(setRole(decoded.role));
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);

            window.location.href = decoded.role === 'ADMIN' ? '/admin/dashboard' : '/dashboard';
        } catch (err) {
            const errorMessage = err.response?.data?.message || err.message || 'Login failed. Please try again.';
            setError(errorMessage);
            console.error('Login Error:', errorMessage);
        } finally {
            setLoading(false);
        }
    };

    const handleReturnToHome = () => navigate('/');
    const handleGoToRegister = () => navigate('/signup');

    return (
        <div className="login-page">
            <Container maxWidth="xs" className="login-container">
                <Box className="login-box" sx={{ p: 4, boxShadow: 5, borderRadius: 3, position: 'relative' }}>
                    {/* 返回主页按钮 */}
                    <IconButton onClick={handleReturnToHome} sx={{ position: 'absolute', top: 8, left: 8 }}>
                        <ArrowBack fontSize="large" color="primary" />
                    </IconButton>

                    {/* 登录标题 */}
                    <Typography variant="h4" gutterBottom align="center">
                        Welcome Back!
                    </Typography>

                    {/* 错误提示 */}
                    {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                    {/* 登录表单 */}
                    <form onSubmit={handleSubmit}>
                        <TextField
                            label="Username"
                            name="username"
                            value={credentials.username}
                            onChange={handleChange}
                            fullWidth
                            margin="normal"
                            required
                        />

                        {/* 密码框添加小眼睛 */}
                        <TextField
                            label="Password"
                            name="password"
                            type={showPassword ? 'text' : 'password'}
                            value={credentials.password}
                            onChange={handleChange}
                            fullWidth
                            margin="normal"
                            required
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            onClick={togglePasswordVisibility}
                                            edge="end"
                                        >
                                            {showPassword ? <VisibilityOff /> : <Visibility />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                        />

                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                            sx={{ mt: 2 }}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} color="inherit" /> : 'Login'}
                        </Button>
                    </form>

                    {/* 前往注册页面 */}
                    <Grid container justifyContent="center" sx={{ mt: 2 }}>
                        <Button variant="text" color="secondary" onClick={handleGoToRegister}>
                            New here? Sign Up Now
                        </Button>
                    </Grid>
                </Box>
            </Container>
        </div>
    );
};

export default LoginPage;
