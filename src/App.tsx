import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Home } from './pages/Home';
import { UniversitiesPage } from './pages/UniversitiesPage';
import { ScoresPage } from './pages/ScoresPage';
import { UniversityDetail } from './pages/UniversityDetail';
import { MajorDetail } from './pages/MajorDetail';
import './styles/index.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/universities" element={<UniversitiesPage />} />
          <Route path="/scores" element={<ScoresPage />} />
          <Route path="/university/:id" element={<UniversityDetail />} />
          <Route path="/university/:universityId/major/:majorId" element={<MajorDetail />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
