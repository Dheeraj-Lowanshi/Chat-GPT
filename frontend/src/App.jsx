import { Routes, Route } from 'react-router-dom'
import AppLayout from './components/AppLayout'
import HomePage from './pages/HomePage'
import AdminLoginPage from './pages/AdminLoginPage'
import CandidateAuthPage from './pages/CandidateAuthPage'
import VoterAuthPage from './pages/VoterAuthPage'
import ElectionListPage from './pages/ElectionListPage'
import VoteCastingPage from './pages/VoteCastingPage'
import ResultPage from './pages/ResultPage'
import AdminDashboardPage from './pages/AdminDashboardPage'
import NotFoundPage from './pages/NotFoundPage'
import ProtectedRoute from './routes/ProtectedRoute'
import VoteConfirmationPage from './pages/VoteConfirmationPage'

export default function App() {
  return (
    <AppLayout>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/admin/login" element={<AdminLoginPage />} />
        <Route path="/candidate/auth" element={<CandidateAuthPage />} />
        <Route path="/voter/auth" element={<VoterAuthPage />} />
        <Route path="/elections" element={<ElectionListPage />} />
        <Route path="/vote/:electionId" element={<ProtectedRoute roles={['VOTER']}><VoteCastingPage /></ProtectedRoute>} />
        <Route path="/vote-confirmation" element={<ProtectedRoute roles={['VOTER']}><VoteConfirmationPage /></ProtectedRoute>} />
        <Route path="/results/:electionId" element={<ResultPage />} />
        <Route path="/admin/dashboard" element={<ProtectedRoute roles={['ADMIN']}><AdminDashboardPage /></ProtectedRoute>} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </AppLayout>
  )
}
