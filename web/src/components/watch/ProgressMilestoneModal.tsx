import Button from '@/components/ui/Button'
import { toast } from 'sonner'
import { Copy, Download, LoaderCircle, Share2, X } from 'lucide-react'
import { useEffect, useState } from 'react'
import type { ProgressMilestone } from '@/types/progressMilestone'

type Props = {
  isOpen: boolean
  milestone: ProgressMilestone | null
  loading?: boolean
  error?: string | null
  onClose: () => void
  onRetry: () => void
}

export default function ProgressMilestoneModal({
  isOpen,
  milestone,
  loading = false,
  error = null,
  onClose,
  onRetry,
}: Props) {
  const [isDownloadingImage, setIsDownloadingImage] = useState(false)
  const [isSharingLink, setIsSharingLink] = useState(false)
  const canUseNativeShare = typeof navigator !== 'undefined' && 'share' in navigator

  useEffect(() => {
    if (!isOpen) return

    function handleEscape(event: KeyboardEvent) {
      if (event.key === 'Escape') {
        onClose()
      }
    }

    document.addEventListener('keydown', handleEscape)

    return () => {
      document.removeEventListener('keydown', handleEscape)
    }
  }, [isOpen, onClose])

  if (!isOpen) return null

  async function handleDownloadImage() {
    if (!milestone || isDownloadingImage) return

    setIsDownloadingImage(true)

    try {
      const response = await fetch(milestone.imageUrl)

      if (!response.ok) {
        throw new Error('Não foi possível baixar a imagem do marco de progresso')
      }

      const imageBlob = await response.blob()
      const blobUrl = window.URL.createObjectURL(imageBlob)
      const downloadLink = document.createElement('a')

      downloadLink.href = blobUrl
      downloadLink.download = `marco-progresso-${milestone.publicCode}.png`
      document.body.appendChild(downloadLink)
      downloadLink.click()
      downloadLink.remove()
      window.URL.revokeObjectURL(blobUrl)

      toast.success('Imagem pronta', {
        description: 'O download do marco de progresso foi iniciado.',
      })
    } catch (downloadError) {
      toast.error('Não foi possível baixar a imagem', {
        description:
          downloadError instanceof Error
            ? downloadError.message
            : 'Tente novamente em alguns instantes.',
      })
    } finally {
      setIsDownloadingImage(false)
    }
  }

  async function handleShareLink() {
    if (!milestone || isSharingLink) return

    setIsSharingLink(true)

    try {
      const shareData = {
        title: `Marco de progresso: ${milestone.courseName}`,
        text: `${milestone.authorName} concluiu ${milestone.totalLessonsCount} aulas do curso ${milestone.courseName} na IMO.`,
        url: milestone.shareUrl,
      }

      if (canUseNativeShare) {
        await navigator.share(shareData)
        return
      }

      await copyToClipboard(milestone.shareUrl)

      toast.success('Link copiado', {
        description: 'O link público do marco de progresso foi copiado para sua área de transferência.',
      })
    } catch (shareError) {
      if (shareError instanceof Error && shareError.name === 'AbortError') {
        return
      }

      toast.error('Não foi possível compartilhar', {
        description:
          shareError instanceof Error
            ? shareError.message
            : 'Tente novamente em alguns instantes.',
      })
    } finally {
      setIsSharingLink(false)
    }
  }

  return (
    <div
      onClick={onClose}
      data-testid="progress-milestone-modal"
      className="fixed inset-0 z-50 flex items-center justify-center bg-[#050311]/80 px-4 py-6 backdrop-blur-sm"
    >
      <div
        onClick={(event) => event.stopPropagation()}
        className="max-h-[90vh] w-full max-w-5xl overflow-y-auto rounded-2xl border border-white/10 bg-[#14082f] p-5 shadow-2xl sm:p-6"
      >
        <div className="flex items-start justify-between gap-4">
          <div className="min-w-0 flex-1">
            <h2 className="mt-4 text-2xl font-bold text-white sm:text-3xl">
              Compartilhar progresso
            </h2>
            <p className="mt-3 max-w-2xl text-sm leading-7 text-white/70 sm:text-base">
              Gere um card público para compartilhar a conclusão do curso na IMO.
            </p>
          </div>

          <button
            type="button"
            onClick={onClose}
            className="cursor-pointer rounded-2xl border border-white/10 bg-white/5 p-3 text-white/70 transition hover:bg-white/10 hover:text-white"
            aria-label="Fechar modal de compartilhamento"
          >
            <X size={18} />
          </button>
        </div>

        {loading ? (
          <div className="mt-6 space-y-5">
            <div className="overflow-hidden rounded-[1.5rem] border border-white/10 bg-[#0b031d] p-4">
              <div className="aspect-[1200/630] animate-pulse rounded-xl bg-white/5" />
            </div>

            <div className="rounded-[1.5rem] border border-white/10 bg-white/5 p-6">
              <LoaderCircle size={28} className="animate-spin text-cyan" />
              <p className="mt-5 text-lg font-semibold text-white">Preparando seu marco</p>
              <p className="mt-2 text-sm leading-7 text-white/70">
                Estamos capturando o snapshot do curso concluído e montando a prévia pública para
                compartilhamento.
              </p>
            </div>
          </div>
        ) : null}

        {!loading && error ? (
          <div className="mt-6 grid gap-4 rounded-[1.5rem] border border-red-400/20 bg-red-500/10 p-6 sm:grid-cols-[minmax(0,1fr)_auto] sm:items-end">
            <div>
              <p className="text-lg font-semibold text-white">Não foi possível preparar o marco</p>
              <p className="mt-2 text-sm leading-7 text-red-100/80">{error}</p>
            </div>

            <div className="flex gap-3">
              <Button
                type="button"
                onClick={onClose}
                className="cursor-pointer rounded-xl border border-white/20 bg-white/5 px-4 py-3 text-sm font-medium text-white! hover:bg-white/10"
              >
                Fechar
              </Button>

              <Button
                type="button"
                onClick={onRetry}
                className="cursor-pointer rounded-xl border border-cyan/40 bg-cyan/10 px-4 py-3 text-sm font-medium text-white! hover:bg-cyan/20"
              >
                Tentar novamente
              </Button>
            </div>
          </div>
        ) : null}

        {!loading && !error && milestone ? (
          <div className="mt-6 space-y-5">
            <div className="overflow-hidden rounded-[1.5rem] border border-white/10 bg-[#0b031d] p-3 sm:p-4">
              <img
                src={milestone.imageUrl}
                alt={`Prévia do marco de progresso do curso ${milestone.courseName}`}
                data-testid="progress-milestone-preview-image"
                className="block aspect-[1200/630] w-full rounded-xl object-cover"
              />
            </div>

            <p className="border-t border-white/10 pt-4 text-sm leading-7 text-white/60">
              Compartilhe o link público ou baixe a imagem para postar onde preferir.
            </p>

            <div className="grid gap-3 sm:grid-cols-2">
              <Button
                type="button"
                onClick={handleShareLink}
                disabled={isSharingLink}
                className="cursor-pointer rounded-2xl border border-cyan/40 bg-cyan/10 px-4 py-3 text-sm font-semibold text-white! hover:bg-cyan/20"
              >
                <span className="flex items-center gap-2">
                  {isSharingLink ? (
                    <LoaderCircle size={16} className="animate-spin" />
                  ) : canUseNativeShare ? (
                    <Share2 size={16} />
                  ) : (
                    <Copy size={16} />
                  )}
                  {isSharingLink
                    ? 'Compartilhando...'
                    : canUseNativeShare
                      ? 'Compartilhar'
                      : 'Copiar link'}
                </span>
              </Button>

              <Button
                type="button"
                onClick={handleDownloadImage}
                disabled={isDownloadingImage}
                className="cursor-pointer rounded-2xl border border-white/20 bg-white/10 px-4 py-3 text-sm font-semibold text-white! hover:bg-white/20"
              >
                <span className="flex items-center gap-2">
                  {isDownloadingImage ? (
                    <LoaderCircle size={16} className="animate-spin" />
                  ) : (
                    <Download size={16} />
                  )}
                  {isDownloadingImage ? 'Baixando imagem...' : 'Baixar imagem'}
                </span>
              </Button>
            </div>
          </div>
        ) : null}
      </div>
    </div>
  )
}

async function copyToClipboard(value: string) {
  if (navigator.clipboard?.writeText) {
    await navigator.clipboard.writeText(value)
    return
  }

  const helperInput = document.createElement('textarea')
  helperInput.value = value
  helperInput.setAttribute('readonly', 'true')
  helperInput.style.position = 'absolute'
  helperInput.style.left = '-9999px'
  document.body.appendChild(helperInput)
  helperInput.select()

  const succeeded = document.execCommand('copy')
  helperInput.remove()

  if (!succeeded) {
    throw new Error('Não foi possível copiar o link para a área de transferência')
  }
}
