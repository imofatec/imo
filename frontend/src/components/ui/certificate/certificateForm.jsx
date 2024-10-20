import { useState } from 'react'
import { InputLabel } from '../inputs/inputlabel'
import { SpinnerButton } from '../spinnerButton'

export default function CertificateForm({
  onValidate,
  setError,
  setCertificateData,
}) {
  const [isLoading, setIsLoading] = useState(false)
  const [certificateId, setCertificateId] = useState('')

  const handleGetCertificate = (e) => {
    e.preventDefault()

    if (certificateId.length === 0) {
      setIsLoading(false)
      return
    }

    setTimeout(() => {
      onValidate(certificateId)
      setIsLoading(false)
    }, 400)
  }
  return (
    <form onSubmit={handleGetCertificate} className="flex flex-col w-96">
      <InputLabel
        label="Codigo"
        type="text"
        id="certificate-id"
        name="certificate-id"
        placeholder="Código do Certificado"
        value={certificateId}
        onChange={(e) => setCertificateId(e.target.value)}
      />

      <SpinnerButton
        children="Validar"
        isLoading={isLoading}
        onClick={() => {
          setIsLoading(true), setError(null), setCertificateData(null)
        }}
        className="bg-custom-header-cyan text-black"
      />
    </form>
  )
}
