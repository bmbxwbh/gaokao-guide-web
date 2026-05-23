import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Card } from '../components/Card';
import { Input } from '../components/Input';
import { Chip } from '../components/Chip';
import { useFilteredUniversities, useUniversityTypes } from '../hooks/useUniversity';
import { getUniversityTypeName } from '../utils/formatters';
import { useAppContext } from '../context/AppContext';
import type { UniversityType } from '../types';
import '../styles/pages.css';

export const UniversitiesPage: React.FC = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedType, setSelectedType] = useState<UniversityType | 'ALL'>('ALL');

  const filteredUniversities = useFilteredUniversities(searchQuery, selectedType);
  const uniqueTypes = useUniversityTypes();
  const { isFavorite, toggleFavorite, addToComparison, isInComparison } = useAppContext();

  return (
    <div className="page-universities">
      <div className="container">
        <header className="page-header">
          <div className="back-btn-container">
            <Link to="/" className="back-btn">
              <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
              </svg>
              返回首页
            </Link>
          </div>
          <h1>蓉城高考指南</h1>
          <p className="subtitle">查询成都市区各大高校历年专业录取分数线</p>
        </header>

        <section className="search-section">
          <Input
            leftIcon={
              <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            }
            placeholder="搜索学校名称..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </section>

        <section className="filter-section">
          <div className="chip-group">
            <Chip
              active={selectedType === 'ALL'}
              onClick={() => setSelectedType('ALL')}
            >
              全部
            </Chip>
            {uniqueTypes.map(type => (
              <Chip
                key={type}
                active={selectedType === type}
                onClick={() => setSelectedType(type)}
              >
                {getUniversityTypeName(type)}
              </Chip>
            ))}
          </div>
        </section>

        <section className="universities-grid">
          {filteredUniversities.length === 0 ? (
            <div className="empty-state">
              <p>没有找到匹配的学校</p>
              <p className="text-sm text-secondary">请尝试其他搜索词</p>
            </div>
          ) : (
            filteredUniversities.map((university, index) => (
              <Card 
                key={university.id} 
                hoverable 
                className="university-card" 
                style={{ animationDelay: `${index * 50}ms` }}
              >
                <div className="card-actions">
                  <button 
                    className={`favorite-btn ${isInComparison('UNIVERSITY', university.id) ? 'comparing' : ''}`}
                    onClick={(e) => {
                      e.preventDefault();
                      e.stopPropagation();
                      addToComparison({
                        id: Date.now().toString(),
                        type: 'UNIVERSITY',
                        targetId: university.id
                      });
                    }}
                    title="添加对比"
                  >
                    <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                    </svg>
                  </button>
                  <button 
                    className={`favorite-btn ${isFavorite('UNIVERSITY', university.id) ? 'active' : ''}`}
                    onClick={(e) => {
                      e.preventDefault();
                      e.stopPropagation();
                      toggleFavorite('UNIVERSITY', university.id);
                    }}
                    title="收藏"
                  >
                    <svg width="20" height="20" fill={isFavorite('UNIVERSITY', university.id) ? 'currentColor' : 'none'} stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                    </svg>
                  </button>
                </div>
                <Link
                  to={`/university/${university.id}`}
                  style={{ textDecoration: 'none', color: 'inherit', display: 'block' }}
                >
                  <div className="university-header">
                    <div className="university-info">
                      <h3>{university.name}</h3>
                      <p className="university-short-name">{university.shortName}</p>
                    </div>
                    <Chip variant="secondary" onClick={(e) => e.preventDefault()}>
                      {getUniversityTypeName(university.type)}
                    </Chip>
                  </div>

                  <p className="university-description">
                    {university.description}
                  </p>

                  {university.overviewScores['2025'] && (
                    <div className="score-grid">
                      <div className="score-item">
                        <div className="score-value">{university.overviewScores['2025'].scienceLow || '-'}</div>
                        <div className="score-label">理科最低</div>
                      </div>
                      <div className="score-item">
                        <div className="score-value">{university.overviewScores['2025'].scienceAvg || '-'}</div>
                        <div className="score-label">理科平均</div>
                      </div>
                      <div className="score-item">
                        <div className="score-value">{university.overviewScores['2025'].liberalArtsLow || '-'}</div>
                        <div className="score-label">文科最低</div>
                      </div>
                      <div className="score-item">
                        <div className="score-value">{university.overviewScores['2025'].liberalArtsAvg || '-'}</div>
                        <div className="score-label">文科平均</div>
                      </div>
                    </div>
                  )}
                </Link>
              </Card>
            ))
          )}
        </section>
      </div>
    </div>
  );
};
