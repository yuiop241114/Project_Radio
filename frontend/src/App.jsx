import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'
//import { BrowerRouter, Routers, Router, BrowserRouter } from 'react-router-dom'

import Login from './components/common/Login'
import SignUp from './components/common/SignUp'
import MainPage from './pages/MainPage'

function App() {
  return (
    <>
      <MainPage/>
      {/*<SignUp/>*/}
    </>
  );
}

export default App
