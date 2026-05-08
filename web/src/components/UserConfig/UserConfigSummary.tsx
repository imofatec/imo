import Button from '@/components/ui/Button'
import type { CurrentUserProfile } from '@/types/user'
import { Camera, LoaderCircle } from 'lucide-react'
import { useRef, type ChangeEvent } from 'react'
import { Link } from 'react-router-dom'

type Props = {
  user: CurrentUserProfile | null
  profileImageSrc: string | null
  hasPendingPhoto: boolean
  isUploadingPhoto: boolean
  photoErrorMessage: string | null
  onSelectPhoto: (file: File | null) => void
  onUploadPhoto: () => Promise<void>
}

export default function UserConfigSummary({
  user,
  profileImageSrc,
  hasPendingPhoto,
  isUploadingPhoto,
  photoErrorMessage,
  onSelectPhoto,
  onUploadPhoto,
}: Props) {
  const fileInputRef = useRef<HTMLInputElement | null>(null)
  const userName = user?.name?.trim() || 'Usuário'
  const userEmail = user?.email || 'E-mail'

  function handleOpenFilePicker() {
    fileInputRef.current?.click()
  }

  function handleFileChange(event: ChangeEvent<HTMLInputElement>) {
    const file = event.target.files?.[0] ?? null
    onSelectPhoto(file)
    event.target.value = ''
  }

  return (
    <div className="rounded-3xl border border-white/10 bg-[#14082f] p-6">
      <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
        <div className="flex items-center gap-4">
          <div className="group relative">
            <input
              ref={fileInputRef}
              type="file"
              accept="image/*"
              className="hidden"
              onChange={handleFileChange}
            />

            <div className="border-cyan/40 bg-cyan/10 text-cyan flex h-16 w-16 items-center justify-center overflow-hidden rounded-full border text-xl font-semibold">
              {profileImageSrc ? (
                <img src={profileImageSrc} alt={userName} className="h-full w-full object-cover" />
              ) : (
                userName.charAt(0).toUpperCase()
              )}
            </div>

            <Button
              type="button"
              onClick={handleOpenFilePicker}
              className="text-cyan! absolute inset-0 flex items-center justify-center rounded-full bg-black/45 opacity-0 transition-all duration-200 group-hover:opacity-100"
            >
              <Camera size={28} />
            </Button>
          </div>

          <div>
            <p className="text-lg font-semibold text-white">{userName}</p>
            <p className="text-sm text-white/60">{userEmail}</p>
          </div>
        </div>

        <div className="flex flex-wrap justify-end gap-3">
          <Link
            to={user?.id ? `/social/${user.id}` : '/user/configuracoes'}
            className="border-cyan/30 bg-cyan/10 text-cyan hover:bg-cyan/20 inline-flex items-center justify-center rounded-full border px-4 py-2 text-sm font-medium transition"
          >
            Ver meu perfil
          </Link>
          <Link
            to="/user/conquistas"
            className="inline-flex items-center justify-center rounded-full border border-white/15 bg-white/5 px-4 py-2 text-sm font-medium text-white transition hover:bg-white/10"
          >
            Ver conquistas
          </Link>
          <Button
            type="button"
            variant="cyanOutline"
            onClick={onUploadPhoto}
            disabled={!hasPendingPhoto || isUploadingPhoto}
            className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
          >
            {isUploadingPhoto ? <LoaderCircle className="animate-spin" /> : 'Salvar foto'}
          </Button>
        </div>
      </div>

      {photoErrorMessage && (
        <p role="alert" className="mt-4 text-sm text-red-500">
          {photoErrorMessage}
        </p>
      )}
    </div>
  )
}
