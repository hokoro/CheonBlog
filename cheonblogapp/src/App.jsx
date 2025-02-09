import './App.css';
import {BrowserRouter as Router , Routes, Route} from "react-router-dom";
import MainLayout from "./Layout/MainLayout/jsx/MainLayout";
import UserLayout from "./Layout/UserLayout/jsx/UserLayout";
import SignUp from "./Component/SignUp";


function App(){
  return(
      <Router>
          <Routes>
              <Route path="/" element={<MainLayout/>}>
              </Route>

              <Route element={<UserLayout/>}>
                  <Route path="/signup" element={<SignUp/>}/>
              </Route>
          </Routes>

      </Router>
  );
}
export default App;
