import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router} from "react-router-dom";
import ProviderWrapper from "./contextsholders/providers/ProviderWrapper";
import HeaderSidebar from "./components/HeaderSidebar";

function App() {
  return (
    <ProviderWrapper>
      <Router>
        <HeaderSidebar />
      </Router>
    </ProviderWrapper>
  );
}
export default App;
