import './App.css';
import {BrowserRouter as Router , Routes, Route} from "react-router-dom";
import MainLayout from "./Layout/MainLayout/jsx/MainLayout";
import UserLayout from "./Layout/UserLayout/jsx/UserLayout";
import SignUp from "./Component/SignUp";
import Login from "./Component/Login";


function App(){
  return(
      <Router>
          <Routes>
              <Route path="/" element={<MainLayout/>}>
              </Route>

              <Route element={<UserLayout/>}>
                  <Route path="/signup" element={<SignUp/>}/>
                  <Route path="/login" element={<Login/>}/>
              </Route>
          </Routes>

      </Router>
  );
}
export default App;
