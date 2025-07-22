import React, { useState } from 'react';
import './App.css';

function App() {
  const [originalUrl, setOriginalUrl] = useState('');
  const [shortCode, setShortCode] = useState('');
  const [expiryTime, setExpiryTime] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setShortCode('');
    setExpiryTime('');

    if (!originalUrl.trim()) {
      setError('Please enter a URL.');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/shorten', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ originalUrl })
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Something went wrong');
      }

      const data = await response.json();
      setShortCode(data.shortCode);
      setExpiryTime(data.expiryTime);
    } catch (err) {
      setError(err.message || 'Something went wrong');
    }
  };

  return (
    <div className="App">
      <h1>URL Shortener</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={originalUrl}
          onChange={(e) => setOriginalUrl(e.target.value)}
          placeholder="Enter the URL to shorten"
        />
        <button type="submit">Shorten</button>
      </form>

      {error && <p style={{ color: 'red' }}>{error}</p>}

      {shortCode && (
        <div style={{ marginTop: '20px' }}>
          <p><strong>Shortened URL:</strong> <a href={`http://localhost:8080/${shortCode}`} target="_blank" rel="noopener noreferrer">http://localhost:8080/{shortCode}</a></p>
          <p><strong>Expires At:</strong> {expiryTime}</p>
        </div>
      )}
    </div>
  );
}

export default App;
