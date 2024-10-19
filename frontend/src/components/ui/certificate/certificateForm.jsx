import { useState } from 'react'
import { Button } from '@/components/ui/button'

export default function CertificateForm({ onValidate }) {
  const [certificateId, setCertificateId] = useState('')

  const handleGetCertificate = (e) => {
    e.preventDefault()
    onValidate(certificateId)
  }
  return (
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
      <Button type="submit" className="w-1/3 bg-custom-header-cyan text-black">
        Validar Certificado
      </Button>
    </form>
  )
}
