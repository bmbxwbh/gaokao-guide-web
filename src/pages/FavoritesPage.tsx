import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { useAppContext } from '../context/AppContext';
import { universities } from '../data/universities';
import { getUniversityTypeName } from '../utils/formatters';
import '../styles/pages.css';

export const FavoritesPage: React.FC = () => {
  const navigate = useNavigate();
  const { favorites, toggleFavorite, addToComparison, isInComparison } = useAppContext();
  const [activeTab, setActiveTab] = useState<'UNIVERSITIES' | 'MAJORS'>('UNIVERSITIES');

  const favoriteUniversities = favorites.filter(f => f.type === 'UNIVERSITY');
  const favoriteMajors = favorites.filter(f => f.type === 'MAJOR');

  const getUniversityById = (id: string) => universities.find(u => u.id === id);

  const getMajorById = (universityId: string, majorId: string) => {
    const university = getUniversityById(universityId);
    return university?.majors.find(m => m.id === majorId);
  };

  return (
    <div className="page-container">
      <div className="page-header">
        <div className="back-button" onClick={() => navigate('/')}>
          <svg width="24" height="24" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
          </svg>
          返回
        </div>
        <h1>我的收藏</h1>
      </div>

      <div className="tabs">
        <div 
          className={`tab ${activeTab === 'UNIVERSITIES' ? 'tab-active' : ''}`} 
          onClick={() => setActiveTab('UNIVERSITIES')}
        >
          学校 ({favoriteUniversities.length})
        </div>
        <div 
          className={`tab ${activeTab === 'MAJORS' ? 'tab-active' : ''}`} 
          onClick={() => setActiveTab('MAJORS')}
        >
          专业 ({favoriteMajors.length})
        </div>
      </div>

      {activeTab === 'UNIVERSITIES' && (
        <div className="favorites-list">
          {favoriteUniversities.length === 0 ? (
            <div className="empty-state">
              <div className="empty-icon">📭</div>
              <h3>暂无收藏</h3>
              <p>去学校列表页收藏感兴趣的学校吧</p>
              <Link to="/universities">
                <Button>去看看</Button>
              </Link>
            </div>
          ) : (
            <div className="universities-grid">
              {favoriteUniversities.map(favorite => {
                const university = getUniversityById(favorite.targetId);
                if (!university) return null;
                
                return (
                  <Card key={favorite.id} className="university-card">
                    <div className="card-actions">
                      <button 
                        className={`favorite-btn ${isInComparison('UNIVERSITY', university.id) ? 'comparing' : ''}`}
                        onClick={() => addToComparison({
                          id: Date.now().toString(),
                          type: 'UNIVERSITY',
                          targetId: university.id
                        })}
                        title="添加对比"
                      >
                        <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                        </svg>
                      </button>
                      <button 
                        className="favorite-btn active"
                        onClick={() => toggleFavorite('UNIVERSITY', university.id)}
                        title="取消收藏"
                      >
                        <svg width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
                          <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
                        </svg>
                      </button>
                    </div>
                    <Link to={`/university/${university.id}`} className="university-card-content">
                      <div className="university-badge">
                        {getUniversityTypeName(university.type)}
                      </div>
                      <h3 className="university-name">{university.name}</h3>
                      <p className="university-location">📍 {university.location.district}</p>
                    </Link>
                  </Card>
                );
              })}
            </div>
          )}
        </div>
      )}

      {activeTab === 'MAJORS' && (
        <div className="favorites-list">
          {favoriteMajors.length === 0 ? (
            <div className="empty-state">
              <div className="empty-icon">📭</div>
              <h3>暂无收藏</h3>
              <p>去专业列表页收藏感兴趣的专业吧</p>
              <Link to="/scores">
                <Button>去看看</Button>
              </Link>
            </div>
          ) : (
            <div className="majors-list">
              {favoriteMajors.map(favorite => {
                const university = getUniversityById(favorite.universityId || '');
                const major = favorite.universityId ? getMajorById(favorite.universityId, favorite.targetId) : null;
                if (!university || !major) return null;

                const latestYear = Object.keys(major.scores).sort().reverse()[0];
                const score = latestYear ? major.scores[latestYear] : null;

                return (
                  <Card key={favorite.id} className="major-result-card">
                    <div className="card-actions">
                      <button 
                        className={`favorite-btn ${isInComparison('MAJOR', major.id) ? 'comparing' : ''}`}
                        onClick={() => addToComparison({
                          id: Date.now().toString(),
                          type: 'MAJOR',
                          targetId: major.id,
                          universityId: university.id
                        })}
                        title="添加对比"
                      >
                        <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                        </svg>
                      </button>
                      <button 
                        className="favorite-btn active"
                        onClick={() => toggleFavorite('MAJOR', major.id, university.id)}
                        title="取消收藏"
                      >
                        <svg width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
                          <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
                        </svg>
                      </button>
                    </div>
                    <Link 
                      to={`/university/${university.id}/major/${major.id}`}
                      className="major-result-content"
                    >
                      <div className="major-result-university">{university.shortName}</div>
                      <div className="major-result-name">{major.name}</div>
                      <div className="major-result-info">
                        <span>专业代码：{major.code}</span>
                        <span>所属学院：{major.department}</span>
                      </div>
                      {score && (
                        <div className="major-result-scores">
                          <div>
                            <span className="score-label">平均分</span>
                            <span className="score-value">{score.avgScore}</span>
                          </div>
                          <div>
                            <span className="score-label">最低分</span>
                            <span className="score-value">{score.lowScore}</span>
                          </div>
                        </div>
                      )}
                    </Link>
                  </Card>
                );
              })}
            </div>
          )}
        </div>
      )}
    </div>
  );
};
