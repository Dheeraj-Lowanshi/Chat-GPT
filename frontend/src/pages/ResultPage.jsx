import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import api from '../api/client'

export default function ResultPage() {
  const [results, setResults] = useState([])
  const { electionId } = useParams()

  useEffect(() => {
    const load = () => api.get(`/elections/${electionId}/results`).then((res) => setResults(res.data)).catch(() => setResults([]))
    load()
    const timer = setInterval(load, 5000)
    return () => clearInterval(timer)
  }, [electionId])

  return <div className="space-y-3"><h2 className="text-2xl font-semibold">Election Results</h2>{results.length===0?<p className="text-slate-600">Results unavailable yet.</p>:results.map((r)=><div key={r.candidateId} className="bg-white p-4 rounded shadow flex justify-between"><div>{r.candidateName} ({r.partyName})</div><strong>{r.votes}</strong></div>)}</div>
}
