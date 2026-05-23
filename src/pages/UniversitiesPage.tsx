import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Card } from '../components/Card';
import { Input } from '../components/Input';
import { Chip } from '../components/Chip';
import { useFilteredUniversities, useUniversityTypes } from '../hooks/useUniversity';
import { getUniversityTypeName } from '../utils/formatters';
import type { UniversityType } from '../types';
import '../styles/pages.css';

export const UniversitiesPage: React.FC = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedType, setSelectedType] = useState<UniversityType | 'ALL'>('ALL');

  const filteredUniversities = useFilteredUniversities(searchQuery, selectedType);
  const uniqueTypes = useUniversityTypes();

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
              <Link
                key={university.id}
                to={`/university/${university.id}`}
                style={{ textDecoration: 'none' }}
              >
                <Card hoverable className="university-card" style={{ animationDelay: `${index * 50}ms` }}>
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
                </Card>
              </Link>
            ))
          )}
        </section>
      </div>
    </div>
  );
};
