import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { PlusIcon } from '@heroicons/react/24/outline'
import { useAuth } from '@/hooks/useAuth'
import { Button } from '@/components/Button'
import { Badge } from '@/components/Badge'
import { AlertDialog } from '@/components/AlertDialog'
import { toast } from '@/components/Toast'
import {
  useIntegrations,
  useDeleteIntegration,
  type IntegrationResponse,
} from '@/features/integrations/hooks/useIntegrations'

const PageHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: ${({ theme }) => theme.spacing[6]};
`

const PageTitle = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  font-size: ${({ theme }) => theme.fontSize.sm};
`

const Thead = styled.thead`
  background-color: ${({ theme }) => theme.colors.gray[50]};
`

const Th = styled.th`
  padding: ${({ theme }) => `${theme.spacing[3]} ${theme.spacing[4]}`};
  text-align: left;
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[500]};
  text-transform: uppercase;
  font-size: ${({ theme }) => theme.fontSize.xs};
  letter-spacing: 0.05em;
  border-bottom: 1px solid ${({ theme }) => theme.colors.gray[200]};
`

const Td = styled.td`
  padding: ${({ theme }) => `${theme.spacing[3]} ${theme.spacing[4]}`};
  color: ${({ theme }) => theme.colors.gray[900]};
  border-bottom: 1px solid ${({ theme }) => theme.colors.gray[100]};
  vertical-align: middle;
`

const Tr = styled.tr`
  &:hover {
    background-color: ${({ theme }) => theme.colors.gray[50]};
  }
`

const ActionsCell = styled(Td)`
  display: flex;
  gap: ${({ theme }) => theme.spacing[2]};
  align-items: center;
`

const EmptyState = styled.div`
  text-align: center;
  padding: ${({ theme }) => `${theme.spacing[16]} ${theme.spacing[4]}`};
  color: ${({ theme }) => theme.colors.gray[500]};
`

const EmptyTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.base};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[700]};
  margin: 0 0 ${({ theme }) => theme.spacing[2]};
`

const EmptyDescription = styled.p`
  font-size: ${({ theme }) => theme.fontSize.sm};
  margin: 0 0 ${({ theme }) => theme.spacing[4]};
`

const ErrorMessage = styled.p`
  color: ${({ theme }) => theme.colors.red[600]};
  font-size: ${({ theme }) => theme.fontSize.sm};
`

function formatSystem(system: string): string {
  const map: Record<string, string> = {
    FASTCHICKENS_HR: 'FastChickensHR',
    STATE_OF_MICHIGAN: 'State of Michigan',
    WORKDAY: 'Workday',
    ADP: 'ADP',
    BENEFITFOCUS: 'Benefitfocus',
  }
  return map[system] ?? system
}

function formatDate(iso: string): string {
  return new Date(iso).toLocaleDateString(undefined, { year: 'numeric', month: 'short', day: 'numeric' })
}

interface DeleteConfirmState {
  open: boolean
  integrationId: string
  name: string
}

export function IntegrationListPage() {
  const navigate = useNavigate()
  const { memberRole } = useAuth()
  const { data: integrations, isLoading, error } = useIntegrations()
  const deleteIntegration = useDeleteIntegration()
  const [deleteConfirm, setDeleteConfirm] = useState<DeleteConfirmState>({
    open: false,
    integrationId: '',
    name: '',
  })

  const canMutate = memberRole !== 'VIEWER'
  const canDelete = memberRole === 'ADMIN' || memberRole === 'OWNER'

  if (isLoading) {
    return <p style={{ color: 'var(--color-gray-500)', fontSize: '0.875rem' }}>Loading…</p>
  }

  if (error) {
    return <ErrorMessage>Failed to load integrations. Please refresh.</ErrorMessage>
  }

  const handleDelete = async () => {
    try {
      await deleteIntegration.mutateAsync(deleteConfirm.integrationId)
      toast.success(`"${deleteConfirm.name}" deleted`)
    } catch {
      toast.error('Failed to delete. Please try again.')
    } finally {
      setDeleteConfirm({ open: false, integrationId: '', name: '' })
    }
  }

  return (
    <>
      <PageHeader>
        <PageTitle>Integrations</PageTitle>
        {canMutate && (
          <Button onClick={() => navigate('/integrations/new')}>
            <PlusIcon style={{ width: '1rem', height: '1rem' }} />
            New Integration
          </Button>
        )}
      </PageHeader>

      {!integrations || integrations.length === 0 ? (
        <EmptyState>
          <EmptyTitle>No integrations yet</EmptyTitle>
          <EmptyDescription>
            Create your first integration to start configuring EDI data pipelines.
          </EmptyDescription>
          {canMutate && (
            <Button onClick={() => navigate('/integrations/new')}>
              <PlusIcon style={{ width: '1rem', height: '1rem' }} />
              New Integration
            </Button>
          )}
        </EmptyState>
      ) : (
        <Table>
          <Thead>
            <tr>
              <Th>Name</Th>
              <Th>From</Th>
              <Th>To</Th>
              <Th>Format</Th>
              <Th>Direction</Th>
              <Th>Created</Th>
              <Th>Actions</Th>
            </tr>
          </Thead>
          <tbody>
            {integrations.map((integration: IntegrationResponse) => (
              <Tr key={integration.integrationId}>
                <Td>{integration.name}</Td>
                <Td>{formatSystem(integration.fromSystem)}</Td>
                <Td>{formatSystem(integration.toSystem)}</Td>
                <Td>{integration.format}</Td>
                <Td>
                  <Badge
                    $variant={
                      integration.direction?.toLowerCase() === 'inbound' ? 'inbound' : 'outbound'
                    }
                  >
                    {integration.direction}
                  </Badge>
                </Td>
                <Td>{formatDate(integration.createdAt)}</Td>
                <ActionsCell>
                  <Button
                    $variant="ghost"
                    $size="sm"
                    onClick={() => navigate(`/integrations/${integration.integrationId}`)}
                  >
                    View
                  </Button>
                  {canMutate && (
                    <Button
                      $variant="secondary"
                      $size="sm"
                      onClick={() =>
                        navigate(`/integrations/${integration.integrationId}/edit`)
                      }
                    >
                      Edit
                    </Button>
                  )}
                  {canDelete && (
                    <Button
                      $variant="danger"
                      $size="sm"
                      onClick={() =>
                        setDeleteConfirm({
                          open: true,
                          integrationId: integration.integrationId,
                          name: integration.name,
                        })
                      }
                    >
                      Delete
                    </Button>
                  )}
                </ActionsCell>
              </Tr>
            ))}
          </tbody>
        </Table>
      )}

      <AlertDialog
        open={deleteConfirm.open}
        onOpenChange={(open) => setDeleteConfirm((prev) => ({ ...prev, open }))}
        title="Delete integration?"
        description={`"${deleteConfirm.name}" will be removed. This action cannot be undone from the UI.`}
        confirmLabel="Delete"
        onConfirm={handleDelete}
      />
    </>
  )
}
