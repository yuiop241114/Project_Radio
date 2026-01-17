import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'
import { BrowserRouter, Route, Routes, Outlet } from 'react-router-dom';

import MainPage from './pages/MainPage';
import Login from './components/common/Login';
import MainLayout from './pages/layout/MainLayout';
import SignUp from './components/common/SignUp';

import PostList from './components/posts/PostList';
import PostDetail from './components/posts/PostDetail';
import PostWrite from './components/posts/PostWrite';
import PostEdit from './components/posts/PostEdit';
import RadioPage from './pages/RadioPage';

function App() {
  return (
    <Routes>
      {/* Header 포함 영역 */}
      <Route element={<MainLayout />}>
        <Route path="/" element={<MainPage/>} />
        <Route path="/post" element={<PostList/>}/>
        <Route path="/post/detail/:postId" element={<PostDetail/>}/>
        <Route path="/post/write" element={<PostWrite/>}/>
        <Route path="/post/edit/:postId" element={<PostEdit/>}/>
        <Route path="/radio" element={<RadioPage/>}/>
      </Route>

      {/* Header 없는 페이지 */}
      <Route path="/login" element={<Login />} />
      <Route path="/signUp" element={<SignUp/>}/>
    </Routes>
  );
}

export default App
