import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../api/client'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

export default function VoteCastingPage() {
  const [candidates, setCandidates] = useState([])
  const { electionId } = useParams()
  const { user } = useAuth()
  const { notify } = useToast()
  const navigate = useNavigate()

  useEffect(() => {
    api.get(`/candidates/election/${electionId}`).then((res) => setCandidates(res.data))
  }, [electionId])

  const castVote = async (candidateId) => {
    try {
      await api.post('/votes', { voterId: user.userId, candidateId, electionId: Number(electionId) })
      notify('Vote cast successfully')
      navigate('/vote-confirmation')
    } catch (e) {
      notify(e?.response?.data?.message || 'Unable to cast vote', 'error')
    }
  }

  return <div className="space-y-3"><h2 className="text-2xl font-semibold">Choose Candidate</h2>{candidates.map((c)=><div key={c.id} className="bg-white p-4 rounded shadow flex justify-between"><div><h3 className="font-semibold">{c.fullName}</h3><p className="text-sm">{c.partyName}</p></div><button onClick={()=>castVote(c.id)} className="px-3 py-1 bg-indigo-600 text-white rounded">Vote</button></div>)}</div>
}
