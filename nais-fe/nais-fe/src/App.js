import logo from './logo.svg';
import './App.css';
import PortfolioReportPage from './pages/PortfolioReportPage';
import LeadReportPage from './pages/LeadsReportsPage';
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";

function App() {
  return (
    <Router>
      <div className="App">
        <nav style={{ padding: "20px", background: "#f4f4f4" }}>
          <Link to="/" style={{ marginRight: "20px" }}>Home</Link>
          <Link to="/report" style={{ marginRight: "20px" }}>Portfolio report</Link>
          <Link to="/lead-report">Leads report</Link>
        </nav>

        <Routes>
          <Route path="/" element={<h1>Welcome to the Dashboard</h1>} />
          <Route path="/report" element={<PortfolioReportPage />} />
          <Route path='/lead-report' element={<LeadReportPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
