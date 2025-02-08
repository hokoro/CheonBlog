import './App.css';
import {BrowserRouter as Router , Routes, Route} from "react-router-dom";
import MainLayout from "./Layout/MainLayout/jsx/MainLayout";


function App(){
  return(
      <Router>
          <Routes>
              <Route path="/" element={<MainLayout/>}>

              </Route>
          </Routes>
      </Router>
  );
}
export default App;
