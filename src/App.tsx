import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Home } from './pages/Home';
import { UniversitiesPage } from './pages/UniversitiesPage';
import { ScoresPage } from './pages/ScoresPage';
import { UniversityDetail } from './pages/UniversityDetail';
import { MajorDetail } from './pages/MajorDetail';
import { RecommendationPage } from './pages/RecommendationPage';
import { FavoritesPage } from './pages/FavoritesPage';
import { ComparisonPage } from './pages/ComparisonPage';
import './styles/index.css';

function App() {
  const isProduction = window.location.hostname !== 'localhost' && 
                      window.location.hostname !== '127.0.0.1'
  
  return (
    <Router basename={isProduction ? '/gaokao-guide-web' : '/'}>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/universities" element={<UniversitiesPage />} />
          <Route path="/scores" element={<ScoresPage />} />
          <Route path="/university/:id" element={<UniversityDetail />} />
          <Route path="/university/:universityId/major/:majorId" element={<MajorDetail />} />
          <Route path="/recommendation" element={<RecommendationPage />} />
          <Route path="/favorites" element={<FavoritesPage />} />
          <Route path="/comparison" element={<ComparisonPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
