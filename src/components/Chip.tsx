import React from 'react';
import '../styles/components.css';

interface ChipProps extends React.HTMLAttributes<HTMLSpanElement> {
  children: React.ReactNode;
  active?: boolean;
  variant?: 'primary' | 'secondary';
}

export const Chip: React.FC<ChipProps> = ({
  children,
  active = false,
  variant = 'primary',
  className = '',
  onClick,
  ...props
}) => {
  let chipClass = `chip ${variant === 'primary' ? 'chip-primary' : 'chip-secondary'}`;
  if (active) {
    chipClass = 'chip chip-active';
  }
  chipClass = `${chipClass} ${className}`.trim();
  
  return (
    <span 
      className={chipClass}
      onClick={onClick}
      role={onClick ? 'button' : undefined}
      tabIndex={onClick ? 0 : undefined}
      {...props}
    >
      {children}
    </span>
  );
};
