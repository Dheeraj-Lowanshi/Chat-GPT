import { useEffect, useState } from 'react'
import api from '../api/client'

export default function AdminDashboardPage() {
  const [elections, setElections] = useState([])
  const [selectedElection, setSelectedElection] = useState('')
  const [dashboard, setDashboard] = useState(null)
  const [voters, setVoters] = useState({ content: [], number: 0, totalPages: 1 })

  useEffect(() => { api.get('/elections').then((res) => setElections(res.data)) }, [])
  useEffect(() => {
    if (!selectedElection) return
    api.get(`/admin/dashboard/${selectedElection}`).then((res) => setDashboard(res.data))
  }, [selectedElection])

  const loadVoters = (page = 0) => api.get(`/voters?page=${page}&size=10`).then((res) => setVoters(res.data))
  useEffect(() => { loadVoters(0) }, [])

  const closeElection = () => api.patch(`/elections/${selectedElection}/close`).then(() => alert('Election closed'))
  const exportCsv = () => window.open(`${import.meta.env.VITE_API_URL || 'http://localhost:8080/api'}/elections/${selectedElection}/results/export`, '_blank')

  return <div className="space-y-4">
    <h2 className="text-2xl font-semibold">Admin Dashboard</h2>
    <select className="border p-2 rounded" value={selectedElection} onChange={(e)=>setSelectedElection(e.target.value)}>
      <option value="">Select election</option>
      {elections.map((e)=><option key={e.id} value={e.id}>{e.title}</option>)}
    </select>
    {dashboard && <div className="grid md:grid-cols-4 gap-3">{[
      ['Total Voters', dashboard.totalVoters], ['Total Candidates', dashboard.totalCandidates],
      ['Total Votes', dashboard.totalVotesCast], ['Turnout %', dashboard.turnoutPercentage]
    ].map(([k,v])=><div key={k} className="bg-white p-4 rounded shadow"><p className="text-sm text-slate-500">{k}</p><p className="text-2xl font-bold">{v}</p></div>)}</div>}
    <div className="space-x-2"><button disabled={!selectedElection} onClick={closeElection} className="px-3 py-1 bg-red-600 text-white rounded">Close Election</button><button disabled={!selectedElection} onClick={exportCsv} className="px-3 py-1 bg-emerald-600 text-white rounded">Export CSV</button></div>
    <div className="bg-white p-4 rounded shadow"><h3 className="font-semibold mb-2">Voters</h3>{voters.content.map((v)=><div key={v.id} className="py-1 border-b text-sm">{v.fullName} - {v.voterIdNumber} ({v.constituency})</div>)}<div className="mt-2 flex gap-2">{Array.from({length:voters.totalPages},(_,i)=><button key={i} className={`px-2 py-1 rounded ${i===voters.number?'bg-indigo-600 text-white':'bg-slate-200'}`} onClick={()=>loadVoters(i)}>{i+1}</button>)}</div></div>
  </div>
}
