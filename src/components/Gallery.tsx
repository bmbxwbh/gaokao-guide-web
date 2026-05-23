import React, { useState } from 'react';
import '../styles/components.css';
import type { CampusImage } from '../types';

interface GalleryProps {
  images: CampusImage[];
  universityName: string;
}

export const Gallery: React.FC<GalleryProps> = ({ images, universityName }) => {
  const [selectedImage, setSelectedImage] = useState<CampusImage | null>(null);

  if (images.length === 0) {
    return null;
  }

  // 按类别分组显示
  const imagesByCategory: Record<string, CampusImage[]> = images.reduce((acc, img) => {
    if (!acc[img.category]) {
      acc[img.category] = [];
    }
    acc[img.category].push(img);
    return acc;
  }, {} as Record<string, CampusImage[]>);

  const getCategoryName = (category: string): string => {
    const names: Record<string, string> = {
      SCENERY: '校园风景',
      DORMITORY: '宿舍环境',
      DINING_HALL: '食堂美食',
      BUILDING: '特色建筑'
    };
    return names[category] || category;
  };

  return (
    <>
      <div className="gallery-section">
        {Object.entries(imagesByCategory).map(([category, categoryImages]) => (
          <div key={category} className="gallery-category">
            <h3 className="category-title">{getCategoryName(category)}</h3>
            <div className="gallery-grid">
              {categoryImages.map((img, index) => (
                <div
                  key={img.id}
                  className="gallery-item"
                  onClick={() => setSelectedImage(img)}
                >
                  <div className="gallery-placeholder">
                    <svg width="48" height="48" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                    </svg>
                    <span className="gallery-item-text">
                      {getCategoryName(category)} {index + 1}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>

      {/* 图片查看模态框 */}
      {selectedImage && (
        <div className="gallery-modal" onClick={() => setSelectedImage(null)}>
          <div className="gallery-modal-content" onClick={(e) => e.stopPropagation()}>
            <button
              className="gallery-close-btn"
              onClick={() => setSelectedImage(null)}
            >
              <svg width="24" height="24" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
            <div className="gallery-modal-placeholder">
              <svg width="120" height="120" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <p className="gallery-modal-text">{selectedImage.description || getCategoryName(selectedImage.category)}</p>
              <p className="text-sm text-secondary">{universityName}</p>
            </div>
          </div>
        </div>
      )}
    </>
  );
};
