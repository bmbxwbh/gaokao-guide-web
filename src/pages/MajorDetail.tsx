import React from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { useUniversity } from '../hooks/useUniversity';
import {
  getDegreeName,
  getSubjectTypeName,
  getBatchName,
  getRequirementText
} from '../utils/formatters';
import type { Major } from '../types';
import '../styles/pages.css';

export const MajorDetail: React.FC = () => {
  const { universityId, majorId } = useParams<{ universityId: string; majorId: string }>();
  const navigate = useNavigate();

  const university = useUniversity(universityId);
  const major = university?.majors.find((m: Major) => m.id === majorId);

  if (!university || !major) {
    return (
      <div className="major-detail-page">
        <div className="container">
          <div className="empty-state">
          <p>未找到该专业</p>
          <Button onClick={() => navigate('/')}>返回首页</Button>
        </div>
        </div>
      </div>
    );
  }

  return (
    <div className="major-detail-page">
      <div className="container">
        <header className="major-detail-header">
          <Link
            to={`/university/${universityId}`}
            className="back-btn">
            <svg width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
            返回{university.shortName}
          </Link>

          <div className="major-detail-title">
            <h1>{major.name}</h1>
            <p className="major-detail-univ">{university.name}</p>
          </div>
        </header>

        <Card>
          <h3>基本信息</h3>
          <div className="detail-info-grid">
            <div className="info-row">
              <span className="info-label">专业代码</span>
              <span className="info-value">{major.code}</span>
            </div>
            <div className="info-row">
              <span className="info-label">所属学院</span>
              <span className="info-value">{major.department}</span>
            </div>
            <div className="info-row">
              <span className="info-label">学制</span>
              <span className="info-value">{major.duration}年</span>
            </div>
            <div className="info-row">
              <span className="info-label">学位</span>
              <span className="info-value">{getDegreeName(major.degree)}</span>
            </div>
            <div className="info-row">
              <span className="info-label">选考要求</span>
              <span className="info-value">{getRequirementText(major.subjectRequirement)}</span>
            </div>
          </div>
        </Card>

        {Object.keys(major.scores).length > 0 && (
          <Card className="detail-section">
            <h3>历年录取分数</h3>
            <div className="scores-section">
              {Object.entries(major.scores).map(([year, score], index) => (
                <div key={year} className="score-card" style={{ animationDelay: `${index * 100}ms` }}>
                  <h4 className="year-title">{year}年</h4>
                  <div className="score-category">
                    <h5>{getSubjectTypeName(score.subjectType)} · {getBatchName(score.batch)}</h5>
                    <div className="info-row">
                      <span className="info-label">最低分</span>
                      <span className="info-value" style={{ color: 'var(--primary-500)', fontWeight: 700 }}>
                        {score.lowScore}
                      </span>
                    </div>
                    <div className="info-row">
                      <span className="info-label">平均分</span>
                      <span className="info-value" style={{ color: 'var(--primary-500)', fontWeight: 700 }}>
                        {score.avgScore}
                      </span>
                    </div>
                    <div className="info-row">
                      <span className="info-label">最高分</span>
                      <span className="info-value" style={{ color: 'var(--primary-500)', fontWeight: 700 }}>
                        {score.highScore}
                      </span>
                    </div>
                    <div className="info-row">
                      <span className="info-label">省控线</span>
                      <span className="info-value">{score.provinceControlLine}</span>
                    </div>
                    {score.planCount && (
                      <div className="info-row">
                        <span className="info-label">计划招生</span>
                        <span className="info-value">{score.planCount}人</span>
                      </div>
                    )}
                    {score.actualCount && (
                      <div className="info-row">
                        <span className="info-label">实际招生</span>
                        <span className="info-value">{score.actualCount}人</span>
                      </div>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </Card>
        )}

        <Card className="detail-section">
          <h3>专业介绍</h3>
          <p>{major.introduction}</p>
        </Card>

        <Card className="detail-section">
          <h3>培养目标</h3>
          <p>{major.trainingGoal}</p>
        </Card>

        <Card className="detail-section">
          <h3>主要课程</h3>
          <ul className="bullet-list">
            {major.mainCourses.map((course, index) => (
              <li key={index}>{course}</li>
            ))}
          </ul>
        </Card>

        <Card className="detail-section">
          <h3>就业方向</h3>
          <ul className="bullet-list">
            {major.employmentDirections.map((direction, index) => (
              <li key={index}>{direction}</li>
            ))}
          </ul>
        </Card>

        {major.furtherStudyDirections.length > 0 && (
          <Card className="detail-section">
            <h3>深造方向</h3>
            <ul className="bullet-list">
              {major.furtherStudyDirections.map((direction, index) => (
                <li key={index}>{direction}</li>
              ))}
            </ul>
          </Card>
        )}

        {major.relatedMajors.length > 0 && (
          <Card className="detail-section">
            <h3>相关专业</h3>
            <p className="text-secondary">更多专业信息请查看学校详情页</p>
          </Card>
        )}
      </div>
    </div>
  );
};
