import React from 'react';
import '../styles/Footer.css';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-left">
        <p>© {new Date().getFullYear()} SkillBridge by Junaid</p>
      </div>

      <div className="footer-right">
        <a href="https://github.com/insjunaid" target="_blank" rel="noopener noreferrer">GitHub</a>
        <a href="mailto:shariffjunaid2004@gmail.com">Contact</a>
      </div>
    </footer>
  );
};

export default Footer;
