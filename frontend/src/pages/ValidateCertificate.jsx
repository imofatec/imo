import { Titulo } from '@/components/ui/titulo'
import { useState } from 'react'
import { Button } from '@/components/ui/button'

import axios from 'axios'

export default function ValidateCertificate() {
    // Design temporario, sera mudado 
  const [certificateData, setCertificateData] = useState()
  const [certificateId, setCertificateId] = useState('')
  const [error, setError] = useState(null)

  const fetchCertificateData = async (id) => {
    try {
      const response = await axios.get(`/api/user/certificate/${id}`)
      setCertificateData(response.data)
      setError(null)
    } catch (error) {
      setError('Certificado não encontrado.')
    }
  }

  const handleGetCertificate = (e) => {
    e.preventDefault()
    setCertificateData(null)
    fetchCertificateData(certificateId)
  }

  return (
    <>
      <Titulo titulo={'Validar Certificado'}></Titulo>
      <div className="h-screen flex flex-col items-center justify-center gap-10">
        <h1>VALIDAR CERTIFICADO</h1>
        <p>Confira se o seu certificado foi emitido corretamente.</p>
        <form
          action=""
          onSubmit={handleGetCertificate}
          className="flex gap-5 w-3/4"
        >
          <input
            type="text"
            id="certificate-id"
            name="certificate-id"
            placeholder="Código do Certificado"
            value={certificateId}
            onChange={(e) => setCertificateId(e.target.value)}
            className="text-black rounded-lg w-2/3 pl-3"
          />
          <Button
            type="submit"
            className="w-1/3 bg-custom-header-cyan text-black"
          >
            Validar Certificado
          </Button>
        </form>
        {error && <p className="text-red-500">{error}</p>}
        {certificateData && (
          <div>
            <h2>Dados do Certificado:</h2>
            <p>Nome: {certificateData.username}</p>
            <p>Curso: {certificateData.courseName}</p>
            <p>Iniciado: {certificateData.courseStartedAt}</p>
            <p>Finalizado: {certificateData.courseFinishedAt}</p>
            <p>Data de Emissão: {certificateData.issuedAt}</p>
          </div>
        )}
      </div>
    </>
  )
}
