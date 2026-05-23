import React from 'react';
import '../styles/components.css';

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
  children: React.ReactNode;
  className?: string;
  hoverable?: boolean;
}

export const Card: React.FC<CardProps> = ({
  children,
  className = '',
  hoverable = false,
  ...props
}) => {
  const cardClass = `card${hoverable ? ' card-hoverable' : ''} ${className}`.trim();
  
  return (
    <div 
      className={cardClass}
      {...props}
    >
      {children}
    </div>
  );
};
