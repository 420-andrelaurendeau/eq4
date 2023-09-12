import { Container } from 'react-bootstrap';
import './App.css';
import SignupView from './views/Signup';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <>
      {/* Tempest of temps */}
      <Container>
        <SignupView />
      </Container>
    </>
  );
}

export default App;
