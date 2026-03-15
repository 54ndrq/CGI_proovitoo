import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/header/header';
import LandingPage from './pages/landing_page';
import ResultsPage from './pages/results_page';
import './app.css';

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Header />
        <main className="main">
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/reserve" element={<ResultsPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
