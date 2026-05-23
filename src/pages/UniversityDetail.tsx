import React, { useState } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { Chip } from '../components/Chip';
import { Gallery } from '../components/Gallery';
import { useUniversity } from '../hooks/useUniversity';
import { getUniversityTypeName, getDegreeName } from '../utils/formatters';
import type { Major } from '../types';
import '../styles/pages.css';

type TabType = 'majors' | 'scores' | 'gallery' | 'intro';

export const UniversityDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const university = useUniversity(id);
  const [activeTab, setActiveTab] = useState<TabType>('majors');

  if (!university) {
    return (
      <div className="page-detail">
        <div className="container">
          <div className="empty-state">
            <p>未找到该学校</p>
            <Button onClick={() => navigate('/')}>返回首页</Button>
          </div>
        </div>
      </div>
    );
  }

  const hasMajors = university.majors.length > 0;
  const hasImages = university.images.length > 0;

  const tabs = [
    { id: 'majors' as TabType, label: '专业收分' },
    { id: 'scores' as TabType, label: '历年分数线' },
    { id: 'gallery' as TabType, label: '校园相册' },
    { id: 'intro' as TabType, label: '学校介绍' }
  ];

  return (
    <div className="page-detail">
      <div className="container">
        <header className="detail-header">
          <Link to="/" className="back-btn">
            <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
            返回首页
          </Link>

          <div className="university-title">
            <h1>{university.name}</h1>
            <Chip variant="secondary">{getUniversityTypeName(university.type)}</Chip>
          </div>
        </header>

        <Card>
          <h3>基本信息</h3>
          <div className="basic-info-grid">
            <div className="info-row">
              <span className="info-label">建校年份</span>
              <span className="info-value">{university.foundingYear}年</span>
            </div>
            <div className="info-row">
              <span className="info-label">主管部门</span>
              <span className="info-value">{university.department}</span>
            </div>
            <div className="info-row">
              <span className="info-label">所在区县</span>
              <span className="info-value">{university.location.district}</span>
            </div>
            <div className="info-row">
              <span className="info-label">详细地址</span>
              <span className="info-value">{university.address}</span>
            </div>
            <div className="info-row">
              <span className="info-label">联系电话</span>
              <span className="info-value">{university.phone}</span>
            </div>
            <div className="info-row">
              <span className="info-label">官方网站</span>
              <span className="info-value">
                <a
                  href={university.website}
                  target="_blank"
                  rel="noopener noreferrer"
                  style={{ color: 'var(--primary-500)' }}
                >
                  {university.website}
                </a>
              </span>
            </div>
          </div>
        </Card>

        <div className="tabs-wrapper">
          <div className="tabs">
            {tabs.map(tab => (
              <button
                key={tab.id}
                className={`tab ${activeTab === tab.id ? 'active' : ''}`}
                onClick={() => setActiveTab(tab.id)}
              >
                {tab.label}
              </button>
            ))}
          </div>
        </div>

        <div className="tab-content">
          {activeTab === 'majors' && (
            <div>
              {!hasMajors ? (
                <div className="empty-state">
                  <p>暂无专业数据</p>
                  <p className="text-sm text-secondary">请查阅相关文档获取更多信息</p>
                </div>
              ) : (
                <div className="majors-list">
                  {university.majors.map((major: Major, index) => (
                    <Card key={major.id} className="major-card" style={{ animationDelay: `${index * 50}ms` }}>
                      <div className="major-header">
                        <div>
                          <h3>{major.name}</h3>
                          <p className="text-sm text-secondary">{major.code} · {major.department}</p>
                        </div>
                        <Chip variant="primary">{getDegreeName(major.degree)}</Chip>
                      </div>

                      {major.scores['2025'] && (
                        <div className="major-scores">
                          <div className="major-score-item">
                            <div className="major-score-value">{major.scores['2025'].lowScore}</div>
                            <div className="major-score-label">最低分</div>
                          </div>
                          <div className="major-score-item">
                            <div className="major-score-value">{major.scores['2025'].avgScore}</div>
                            <div className="major-score-label">平均分</div>
                          </div>
                          <div className="major-score-item">
                            <div className="major-score-value">{major.scores['2025'].highScore}</div>
                            <div className="major-score-label">最高分</div>
                          </div>
                        </div>
                      )}

                      <div className="major-footer">
                        <Link
                          to={`/university/${university.id}/major/${major.id}`}
                          className="major-detail-link"
                        >
                          查看详情
                          <svg width="16" height="16" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                          </svg>
                        </Link>
                      </div>
                    </Card>
                  ))}
                </div>
              )}
            </div>
          )}

          {activeTab === 'scores' && (
            <div className="scores-section">
              {Object.entries(university.overviewScores).map(([year, scores], index) => (
                <Card key={year} className="score-card" style={{ animationDelay: `${index * 100}ms` }}>
                  <h3 className="year-title">{year}年录取分数线</h3>
                  <div className="score-columns">
                    {(scores.scienceLow || scores.scienceAvg || scores.scienceHigh) && (
                      <div className="score-category">
                        <h4>理科</h4>
                        {scores.scienceLow && (
                          <div className="info-row">
                            <span className="info-label">最低分</span>
                            <span className="info-value">{scores.scienceLow}</span>
                          </div>
                        )}
                        {scores.scienceAvg && (
                          <div className="info-row">
                            <span className="info-label">平均分</span>
                            <span className="info-value">{scores.scienceAvg}</span>
                          </div>
                        )}
                        {scores.scienceHigh && (
                          <div className="info-row">
                            <span className="info-label">最高分</span>
                            <span className="info-value">{scores.scienceHigh}</span>
                          </div>
                        )}
                      </div>
                    )}

                    {(scores.liberalArtsLow || scores.liberalArtsAvg || scores.liberalArtsHigh) && (
                      <div className="score-category">
                        <h4>文科</h4>
                        {scores.liberalArtsLow && (
                          <div className="info-row">
                            <span className="info-label">最低分</span>
                            <span className="info-value">{scores.liberalArtsLow}</span>
                          </div>
                        )}
                        {scores.liberalArtsAvg && (
                          <div className="info-row">
                            <span className="info-label">平均分</span>
                            <span className="info-value">{scores.liberalArtsAvg}</span>
                          </div>
                        )}
                        {scores.liberalArtsHigh && (
                          <div className="info-row">
                            <span className="info-label">最高分</span>
                            <span className="info-value">{scores.liberalArtsHigh}</span>
                          </div>
                        )}
                      </div>
                    )}
                  </div>
                </Card>
              ))}
            </div>
          )}

          {activeTab === 'gallery' && (
            <div className="intro-section">
              {!hasImages ? (
                <div className="empty-state">
                  <p>暂无校园照片</p>
                  <p className="text-sm text-secondary">后续将添加更多图片资料</p>
                </div>
              ) : (
                <Gallery images={university.images} universityName={university.name} />
              )}
            </div>
          )}

          {activeTab === 'intro' && (
            <div className="intro-section">
              <Card>
                <h3>学校简介</h3>
                <p>{university.description}</p>
              </Card>

              <Card>
                <h3>重点学科</h3>
                <ul className="bullet-list key-disciplines">
                  {university.keyDisciplines.map((discipline, index) => (
                    <li key={index}>{discipline}</li>
                  ))}
                </ul>
              </Card>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
