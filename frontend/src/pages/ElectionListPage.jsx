import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api/client'

export default function ElectionListPage() {
  const [elections, setElections] = useState([])

  useEffect(() => {
    api.get('/elections').then((res) => setElections(res.data))
  }, [])

  return (
    <div className="space-y-3">
      <h2 className="text-2xl font-semibold">Elections</h2>
      {elections.map((e) => (
        <div key={e.id} className="bg-white p-4 rounded shadow flex justify-between items-center">
          <div><h3 className="font-semibold">{e.title}</h3><p className="text-sm text-slate-600">{e.description}</p><p className="text-xs">Status: {e.status}</p></div>
          <div className="space-x-2">
            <Link className="px-3 py-1 bg-indigo-600 text-white rounded" to={`/vote/${e.id}`}>Vote</Link>
            <Link className="px-3 py-1 bg-emerald-600 text-white rounded" to={`/results/${e.id}`}>Results</Link>
          </div>
        </div>
      ))}
    </div>
  )
}
