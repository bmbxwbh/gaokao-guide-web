import React from 'react';
import { Link } from 'react-router-dom';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import '../styles/pages.css';

export const Home: React.FC = () => {
  return (
    <div className="page-home-landing">
      <div className="container">
        <header className="hero-section">
          <div className="hero-content">
            <h1>蓉城高考指南</h1>
            <p className="hero-subtitle">
              助力成都学子，圆梦理想大学
            </p>
            <p className="hero-description">
              提供成都市区各大高校历年专业录取分数线查询，
              帮助您科学填报志愿，规划未来。
            </p>
          </div>
        </header>

        <section className="entry-sections">
          <Link to="/universities" className="entry-card-link" style={{ textDecoration: 'none' }}>
            <Card hoverable className="entry-card entry-card-universities">
              <div className="entry-card-icon">
                <svg width="48" height="48" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
                </svg>
              </div>
              <h2 className="entry-card-title">学校主页</h2>
              <p className="entry-card-description">
                浏览成都市区各大高校，查看学校介绍、历年分数线、校园相册等详细信息。
              </p>
              <div className="entry-card-action">
                <Button size="large">进入学校主页</Button>
              </div>
            </Card>
          </Link>

          <Link to="/scores" className="entry-card-link" style={{ textDecoration: 'none' }}>
            <Card hoverable className="entry-card entry-card-scores">
              <div className="entry-card-icon">
                <svg width="48" height="48" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z" />
                </svg>
              </div>
              <h2 className="entry-card-title">收分主页</h2>
              <p className="entry-card-description">
                查看所有学校所有专业历年录取分数线，支持按学校、专业、分数段筛选。
              </p>
              <div className="entry-card-action">
                <Button variant="primary" size="large">进入收分主页</Button>
              </div>
            </Card>
          </Link>
        </section>

        <footer className="home-footer">
          <p>数据仅供参考，请以学校官方发布为准</p>
        </footer>
      </div>
    </div>
  );
};
