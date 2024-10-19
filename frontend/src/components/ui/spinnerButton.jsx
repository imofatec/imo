import { Button } from './button'

export function SpinnerButton({ isLoading, onClick, children, className }) {
  return (
    <>
      <Button className={className} onClick={onClick}>
        {isLoading ? (
          <div className="flex justify-center items-center">
            <div className="w-4 h-4 border-2 border-black border-t-transparent rounded-full animate-spin"></div>
          </div>
        ) : (
          children
        )}
      </Button>
    </>
  )
}
